package com.example.drawer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drawer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemOnBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemOnBoardFragment extends Fragment {
    TextView txtTitle;
    private static final String ARG_PARAM1 = "param1";

    private String title;


    public ItemOnBoardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemOnBoardFragment newInstance(String title) {
        ItemOnBoardFragment fragment = new ItemOnBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_on_board, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        txtTitle = view.findViewById(R.id.txt_title_onboard);
        txtTitle.setText(title);
    }
}