package com.example.drawer;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawer.databinding.FragmentFormBinding;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.models.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {

    private EditText etTitle, etDescription;
    private FragmentFormBinding binding;
    TextView txtReady, txtDayMonth, txtTime;
    NavController navController;

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

        initView(view);
        initClickListener(view);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (destination.getId() == R.id.formFragment){
                    binding.toolbarForm.setVisibility(View.VISIBLE);
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("d MMMM");
                    SimpleDateFormat month_time = new SimpleDateFormat("HH:mm");
                    String month_name = month_date.format(cal.getTime());
                    String moth_time_add = month_time.format(new Date());
                    txtDayMonth.setText(month_name);
                    txtTime.setText(moth_time_add);
//                    getParentFragmentManager().setFragmentResultListener("key", getViewLifecycleOwner(),
//                            (requestKey, result) -> {
//                        int position = (int) result.getSerializable("model");
//
//                    });
                }else {
                    binding.toolbarForm.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    private void initClickListener(View view) {

        txtReady.setOnClickListener(v -> {
            if (etTitle.getText().toString().isEmpty()) {
                etTitle.setError("Please enter title");
            } else if (etDescription.getText().toString().isEmpty()) {
                etDescription.setError("Please enter description");
            } else {
//                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
//                alertDialog.setTitle("Alert");
//                alertDialog.setMessage("Alert message to be shown");
//                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
                SimpleDateFormat sdfTime = new SimpleDateFormat("dd.MM/HH:mm");
                String time = sdfTime.format(new Date());
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                model = new NoteModel(title, description, time);
                Bundle bundle = new Bundle();
                bundle.putSerializable("keyModel", model);
                getParentFragmentManager().setFragmentResult("model", bundle);
                navController.navigateUp();

            }
        });
    }





    private void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        txtDayMonth = view.findViewById(R.id.txt_day_month);
        txtTime = view.findViewById(R.id.txt_time);
        txtReady = view.findViewById(R.id.txt_ready);
    }

}