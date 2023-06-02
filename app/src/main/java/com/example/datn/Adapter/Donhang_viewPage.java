package com.example.datn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.datn.GUI.CXNFragment;
import com.example.datn.GUI.DHFragment;
import com.example.datn.GUI.DaGFragment;
import com.example.datn.GUI.DangGFragment;

public class Donhang_viewPage extends FragmentStateAdapter {
    public Donhang_viewPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CXNFragment();
            case 1:
                return new DangGFragment();
            case 2:
                return new DaGFragment();
            case 3:
                return new DHFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
