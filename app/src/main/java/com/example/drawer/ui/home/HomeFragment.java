package com.example.drawer.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.drawer.activitys.MainActivity;
import com.example.drawer.fragments.FormFragment;
import com.example.drawer.R;
import com.example.drawer.adapters.NoteAdapter;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;
import com.example.drawer.unit.App;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public static SimpleDateFormat sdfTime;
    public static boolean isList = true;

    NoteAdapter adapter = new NoteAdapter();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.e("TAG", "onCreateView: " + App.getInstance(requireContext()).getTaskDao().getAll());
        setAdapter();
        initRecycler();
        getDataForm();
        addTextListener();
        return root;
    }




    private void getDataForm() {
        if (App.getInstance(requireContext()).getTaskDao().getAll() != null){
                adapter.addListOfModel(App.getInstance(requireContext()).getTaskDao().getAll());
        }
    }



    private void addTextListener() {
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }




    private void filter(String text) {

        List<NoteModel> newList = new ArrayList<>();
        for (NoteModel item : adapter.list) {
            if (item.getTitle().contains(text)) {
                newList.add(item);
            }
        }
        if (binding.searchEt.getText().toString().isEmpty()) {
            adapter.listEmpty();
        } else {
            adapter.filterList(newList);
        }


    }

public void setAdapter(){
    binding.rv.setAdapter(adapter);
    binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
}

    private void initRecycler() {

        getParentFragmentManager().setFragmentResultListener("import", getViewLifecycleOwner(), (requestKey, result) -> {
            NoteModel model = (NoteModel) result.getSerializable("keyTitle");
            if (model != null) {
                if (!isList) {
                    binding.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                } else {
                    binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                adapter.addNotes(model, HomeFragment.this);
                App.getInstance(requireContext()).getTaskDao().insertAll(model);
            }
        });
        getParentFragmentManager().setFragmentResultListener("edit", getViewLifecycleOwner(), (requestKey, result) -> {
            NoteModel model = (NoteModel) result.getSerializable("keyTitle");
            adapter.editModel(model, result.getInt("position"));
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_dashboard) {
            if (isList) {
                item.setIcon(R.drawable.ic_baseline_format_list_bulleted_24);
                binding.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                isList = false;
            } else {
                item.setIcon(R.drawable.dashboard);
                binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
                isList = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(int pisition, NoteModel model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mod", model);
        bundle.putSerializable("position", pisition);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.action_nav_home_to_formFragment, bundle);
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("Внимание!");
        dialog.setMessage("Вы действительно хотите удалить");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.delete(position);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}