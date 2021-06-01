package com.example.drawer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawer.models.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {

    private EditText etTitle, etDescription;
    TextView txtReady;

    NoteModel model;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Navigation.findNavController(view)
                .getCurrentDestination().setLabel("Hello");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("d MMMM");
        SimpleDateFormat month_time = new SimpleDateFormat("HH:mm");
        String month_name = month_date.format(cal.getTime());
        String moth_time_add = month_time.format(cal.getTime());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(month_name+ " " + moth_time_add);
        initView(view);
        initClickListener(view);
        return view;
    }

    private void initClickListener(View view) {

        txtReady.setOnClickListener(v -> {
            if (etTitle.getText().toString().isEmpty()) {
                etTitle.setError("Please enter title");
            } else if (etDescription.getText().toString().isEmpty()) {
                etDescription.setError("Please enter description");
            } else {
                SimpleDateFormat sdfTime = new SimpleDateFormat("dd.MM/HH:mm");
                String time = sdfTime.format(new Date());
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                model = new NoteModel(title, description, time);
                Bundle bundle = new Bundle();
                bundle.putSerializable("keyModel", model);
                getParentFragmentManager().setFragmentResult("model", bundle);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigateUp();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ready:
                Toast.makeText(getContext(), "gwgweg", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }

    private void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        txtReady = view.findViewById(R.id.txt_ready);
    }

}