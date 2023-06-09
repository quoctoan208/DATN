package com.example.datn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.datn.Fragment.CXNFragment;
import com.example.datn.Fragment.ChogiaohangFragment;
import com.example.datn.Fragment.DHFragment;
import com.example.datn.Fragment.DaGFragment;
import com.example.datn.Fragment.DangGFragment;
import com.example.datn.Fragment.DonbanCXNFragment;

public class Donhang_viewPage extends FragmentStateAdapter {
    public Donhang_viewPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    private static final Class<?>[] FRAGMENT_CLASSES = {
            CXNFragment.class,
            ChogiaohangFragment.class,
            DangGFragment.class,
            DaGFragment.class,
            DHFragment.class
    };

    public Fragment createFragment(int position) {
        try {
            return (Fragment) FRAGMENT_CLASSES[position].newInstance();
        } catch (IllegalAccessException | InstantiationException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
