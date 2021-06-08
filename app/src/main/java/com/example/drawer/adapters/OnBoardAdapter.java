package com.example.drawer.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.drawer.fragments.ItemOnBoardFragment;
import com.example.drawer.models.OnBoardModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnBoardAdapter extends FragmentStatePagerAdapter {
    List<OnBoardModel> list;
    FragmentManager manager;

    public OnBoardAdapter(@NonNull @NotNull FragmentManager fm, List<OnBoardModel> list) {
        super(fm);
        this.manager = fm;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ItemOnBoardFragment();
        switch (position){
            case 0:
                fragment = ItemOnBoardFragment.newInstance(list.get(position).getTitle());
                break;
            case 1:
                fragment = ItemOnBoardFragment.newInstance(list.get(position).getTitle());
                break;
            case 2:
                fragment = ItemOnBoardFragment.newInstance(list.get(position).getTitle());
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
