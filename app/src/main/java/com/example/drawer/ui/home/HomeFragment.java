package com.example.drawer.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.drawer.FormFragment;
import com.example.drawer.R;
import com.example.drawer.adapters.NoteAdapter;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    NavigationView navigationView;
    private ArrayList<NoteModel> list = new ArrayList<>();
    NavController navController;
    NoteModel model;
    NoteAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.searchNotes(s.toString());
            }
        });
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("your subtitle");
        View root = binding.getRoot();
        initRecycler();
        return root;
    }


    private void initRecycler() {
        binding.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteAdapter();
//        adapter.setListener(new OnItemClickListener() {
//            @Override
//            public void setOnItemClickListener(int position, ArrayList<NoteModel> list) {
//                String title = list.get(position).getTitle();
//                String desc =list.get(position).getDescription();
//                model = new NoteModel(title,desc);
//                Bundle bundle = new Bundle();
//                 bundle.putSerializable("model", model);
////                bundle.putSerializable("position", position);
//                getParentFragmentManager().setFragmentResult("key", bundle);
//
//            }
//        });
        binding.rv.setAdapter(adapter);
        getParentFragmentManager().setFragmentResultListener("model", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                NoteModel model = (NoteModel) result.getSerializable("keyModel");
                adapter.addNotes(model);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}