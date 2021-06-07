package com.example.drawer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.drawer.databinding.FragmentFormBinding;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;
import com.example.drawer.unit.Preference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {

    private EditText etTitle;
    private FragmentFormBinding binding;
    TextView txtReady, txtDayMonth, txtTime;
    OnItemClickListener click;
    private String title;
    private boolean isEdit = false;
    String background;
    Button btnBlack, btnYellow, btnRed;

    int pos;
    public void setClick(OnItemClickListener click) {
        this.click = click;
    }

    SharedPreferences preferences;
    String time;
    NavController navController;



    NoteModel model = new NoteModel();

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
        initView(view);
        getData();
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
            background = "black";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_black));
        });
        btnYellow.setOnClickListener(v -> {
            background = "yellow";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_yellow));
        });
        btnRed.setOnClickListener(v -> {
            background = "red";
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(5)
                    .playOn(v.findViewById(R.id.btn_red));
        });
    }

    private void getData() {
        if (getArguments() != null) {
            model = (NoteModel) getArguments().getSerializable("mod");
            etTitle.setText(model.getTitle());
            pos = getArguments().getInt("position");
            isEdit = true;
        }
    }

    private void initClickListener(View view) {

            txtReady.setOnClickListener(v -> {
                if (etTitle.getText().toString().isEmpty()) {
                    etTitle.setError("Please enter title");
                } else  if (isEdit){
                    SimpleDateFormat sdfTime = new SimpleDateFormat("dd.MM/HH:mm");
                    time = sdfTime.format(new Date());
                    title = etTitle.getText().toString();
                    model = new NoteModel(title, background);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("keyTitle", model);
                    bundle.putInt("position", pos);
                    getParentFragmentManager().setFragmentResult("edit", bundle);
                    navController.navigateUp();
                }else {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("dd.MM/HH:mm");
                    time = sdfTime.format(new Date());
                    title = etTitle.getText().toString();
                    model = new NoteModel(title, background);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("keyTitle", model);
//                    bundle.putString("background", background);
                    getParentFragmentManager().setFragmentResult("import", bundle);
                    navController.navigateUp();
                }
            });

    }


    private void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        txtDayMonth = view.findViewById(R.id.txt_day_month);
        txtTime = view.findViewById(R.id.txt_time);
        txtReady = view.findViewById(R.id.txt_ready);
        btnBlack = view.findViewById(R.id.btn_black);
        btnYellow = view.findViewById(R.id.btn_yellow);
        btnRed = view.findViewById(R.id.btn_red);
    }
}
