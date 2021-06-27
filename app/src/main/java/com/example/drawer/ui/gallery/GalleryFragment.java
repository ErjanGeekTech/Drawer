package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.drawer.R;
import com.example.drawer.adapters.AdapterChat;
import com.example.drawer.databinding.FragmentGalleryBinding;
import com.example.drawer.models.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    AdapterChat adapterChat;
    private FirebaseFirestore firestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        firestore = FirebaseFirestore.getInstance();
        setAdapter();
        setupFirestore();
        getDataFromFirestore();
        return binding.getRoot();
    }

    private void setAdapter() {
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rv.setAdapter(adapterChat);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterChat = new AdapterChat();
    }

    private void getDataFromFirestore() {
        firestore.collection("allmessages")
                .get()
                .addOnCompleteListener((task -> {
                    if (task.isSuccessful()) {
                        List<ChatModel> list = new ArrayList<>();
                        list.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChatModel model = new ChatModel(document.getString("msg"));
                            list.add(model);
//                            adapterChat.addMessage(new ChatModel(document.getString("msg")));
                        }
                        adapterChat.addMessage(list);
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                }));
    }

    private void setupFirestore() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etText.getText().toString().isEmpty()) {
                    binding.etText.setError("Input text");
                } else {
                    Map<String, Object> message = new HashMap<>();
                    message.put("msg", binding.etText.getText().toString());

                    firestore.collection("allmessages")
                            .add(message)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.e("TAG", "setupFirestore: success");
                                } else {
                                    Log.e("TAG", "setupFirestore: error" + task.toString());
                                }
                            });
                    binding.etText.setText("");
                    Toast.makeText(requireContext(), "получилось", Toast.LENGTH_SHORT).show();
                    getDataFromFirestore();
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}