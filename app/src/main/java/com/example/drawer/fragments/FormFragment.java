package com.example.drawer.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentFormBinding;
import com.example.drawer.models.NoteModel;
import com.example.drawer.unit.App;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {

    private EditText etTitle;
    private FragmentFormBinding binding;
    TextView txtReady, txtDayMonth, txtTime;
    private String title;
    String background;
    Button btnBlack, btnYellow, btnRed;
    int pos;
    String time;
    RadioGroup radioGroup;
    NavController navController;
    RadioButton radio_black, radio_yellow, radio_red;
    NoteModel mod;


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
        binding = FragmentFormBinding.inflate(inflater, container, false);
        radioGroup = view.findViewById(R.id.radios);
        view.findViewById(R.id.radio_black).setOnClickListener(this::initRadio);
        view.findViewById(R.id.radio_yellow).setOnClickListener(this::initRadio);
        view.findViewById(R.id.radio_red).setOnClickListener(this::initRadio);
        initView(view);
        getModel();
        iniButtons();
        initClickListener(view);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (destination.getId() == R.id.formFragment) {
                    binding.toolbarForm.setVisibility(View.VISIBLE);
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("d MMMM");
                    SimpleDateFormat month_time = new SimpleDateFormat("HH:mm");
                    String month_name = month_date.format(cal.getTime());
                    String moth_time_add = month_time.format(new Date());
                    txtDayMonth.setText(month_name);
                    txtTime.setText(moth_time_add);
                } else {
                    binding.toolbarForm.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    private void iniButtons() {
        btnBlack.setOnClickListener(v -> {
            radio_black.performClick();
            radio_yellow.setChecked(false);
            radio_red.setChecked(false);
            background = "black";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_black));
        });
        btnYellow.setOnClickListener(v -> {
            radio_yellow.performClick();
            radio_black.setChecked(false);
            radio_red.setChecked(false);
            background = "yellow";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_yellow));
        });
        btnRed.setOnClickListener(v -> {
            radio_red.performClick();
            radio_black.setChecked(false);
            radio_yellow.setChecked(false);
            background = "red";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_red));
        });
    }

    private void initRadio(View view) {
        switch (view.getId()) {
            case R.id.radio_black:
            case R.id.radio_yellow:
            case R.id.radio_red:
                radioGroup.clearCheck();
                radioGroup.check(view.getId());
                break;
        }
    }

    private void getModel() {
        if (getArguments() != null) {
            mod = (NoteModel) getArguments().getSerializable("mod");
            if (mod != null) {
                etTitle.setText(mod.getTitle());
            }
        }
    }

    private void initClickListener(View view) {

        txtReady.setOnClickListener(v -> {
            if (etTitle.getText().toString().isEmpty()) {
                etTitle.setError("Please enter title");
            } else {
                SimpleDateFormat sdfTime = new SimpleDateFormat("d MMMM HH:mm");
                time = sdfTime.format(new Date());
                title = etTitle.getText().toString();
                model = new NoteModel(title, background, time);
                if (mod == null) {
                    App.getInstance().getTaskDao().insertAll(model);
                } else {
                    mod.setTitle(etTitle.getText().toString());
                    mod.setDate(time);
                    mod.setBackground(background);
                    App.getInstance().getTaskDao().update(mod);
                }
                close();
            }
        });

    }

    private void close() {
        navController.navigateUp();
    }

    private void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        txtDayMonth = view.findViewById(R.id.txt_day_month);
        txtTime = view.findViewById(R.id.txt_time);
        txtReady = view.findViewById(R.id.txt_ready);
        btnBlack = view.findViewById(R.id.btn_black);
        btnYellow = view.findViewById(R.id.btn_yellow);
        btnRed = view.findViewById(R.id.btn_red);
        radio_black = view.findViewById(R.id.radio_black);
        radio_yellow = view.findViewById(R.id.radio_yellow);
        radio_red = view.findViewById(R.id.radio_red);
    }

}


