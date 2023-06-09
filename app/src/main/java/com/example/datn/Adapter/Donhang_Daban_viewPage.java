package com.example.datn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.datn.Fragment.CXNFragment;
import com.example.datn.Fragment.DHFragment;
import com.example.datn.Fragment.DaGFragment;
import com.example.datn.Fragment.DangGFragment;
import com.example.datn.Fragment.DonbanCXNFragment;
import com.example.datn.Fragment.DonbanDHFragment;
import com.example.datn.Fragment.DonbanDaGFragment;
import com.example.datn.Fragment.DonbanDangGFragment;
import com.example.datn.Fragment.Donban_ChogiaohangFragment;

public class Donhang_Daban_viewPage extends FragmentStateAdapter {
    public Donhang_Daban_viewPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    private static final Class<?>[] FRAGMENT_CLASSES = {
            DonbanCXNFragment.class,
            Donban_ChogiaohangFragment.class,
            DonbanDangGFragment.class,
            DonbanDaGFragment.class,
            DonbanDHFragment.class
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
