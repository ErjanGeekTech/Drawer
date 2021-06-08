package com.example.drawer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.drawer.R;
import com.example.drawer.adapters.OnBoardAdapter;
import com.example.drawer.models.OnBoardModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout indicator;
    OnBoardAdapter adapter;
TextView txtStart, txtSkip;
LottieAnimationView lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        pager =findViewById(R.id.vp_onboard);
        ininView();
        initButtons();
        checkShowingOnBoard();
        setData();
    }

    private void checkShowingOnBoard() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OnBoardActivity.this);
        Boolean showOnBoard = preferences.getBoolean("showOnBoard", false);
        if (showOnBoard) {
            Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initButtons() {
        txtStart.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
            startActivity(intent);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OnBoardActivity.this);
            preferences.edit().putBoolean("showOnBoard" , true).apply();
        });
        txtSkip.setOnClickListener(v -> {
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        });

    }

    private void ininView() {
        txtStart = findViewById(R.id.txt_start);
        txtSkip = findViewById(R.id.txt_skip);
        lottie = findViewById(R.id.lottie);
        indicator = findViewById(R.id.tab_indicator);
    }

    private void setData() {
        List<OnBoardModel> list = new ArrayList<>();
        list.add(new OnBoardModel("Очень удобный функционал"));
        list.add(new OnBoardModel("Быстрый, качественный продукт"));
        list.add(new OnBoardModel("Куча функций и интересных фишек"));
        adapter = new OnBoardAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
        indicator.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    txtSkip.setVisibility(View.GONE);
                    txtStart.setVisibility(View.VISIBLE);
                }else {
                    txtSkip.setVisibility(View.VISIBLE);
                    txtStart.setVisibility(View.GONE);
                }

                if (position == 1){
                    lottie.setAnimation(R.raw.tapping);
                    lottie.playAnimation();
                }else  if(position == 2){
                    lottie.setAnimation(R.raw.pro);
                    lottie.playAnimation();
                }else if (position == 0){
                    lottie.setAnimation(R.raw.guy);
                    lottie.playAnimation();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}