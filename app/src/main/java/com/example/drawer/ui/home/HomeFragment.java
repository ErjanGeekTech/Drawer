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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    public static FragmentHomeBinding binding;
    public static SimpleDateFormat sdfTime;
    public static boolean isList = true;
    NoteModel noteModel;
    public int positionM;

    List<NoteModel> list;

    NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new NoteAdapter(isList, HomeFragment.this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setAdapter();
        getDataForm();
        initRecycler();
        addTextListener();
        return root;
    }





    private void getDataForm() {
        App.getInstance().getTaskDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                list = noteModels;
                adapter.addListOfModel(noteModels);

            }
        });

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
            NoteModel model = (NoteModel) result.getSerializable("model");
            NoteModel updateModel = (NoteModel) result.getSerializable("updateModel");
                if (!isList) {
                    binding.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                } else {
                    binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                if (model != null){
                    adapter.addNotes(model, HomeFragment.this);
                }else {
                    adapter.editModel(updateModel, positionM);
                }
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
        getDataForm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(int pisition, NoteModel model) {
        Bundle bundle = new Bundle();
        positionM = pisition;
        bundle.putSerializable("mod", model);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.action_nav_home_to_formFragment, bundle);
    }

    @Override
    public void onDeleteSwipe(NoteModel model) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                App.getInstance().getTaskDao().delete(model);
                adapter.delete(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.rv);
    }

}