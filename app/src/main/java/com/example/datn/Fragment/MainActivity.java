package com.example.datn.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn.GUI.GioHangActivity;
import com.example.datn.GUI.ThemSanPham;
import com.example.datn.R;
import com.example.datn.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private long itemId1;
    private long itemId2;
    private long itemId3;
    private long itemId4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MenuItem item1 = binding.bottomNavigationView.getMenu().findItem(R.id.home);
         itemId1 = item1.getItemId();
        MenuItem item2 = binding.bottomNavigationView.getMenu().findItem(R.id.shorts);
         itemId2 = item2.getItemId();
        MenuItem item3 = binding.bottomNavigationView.getMenu().findItem(R.id.subscriptions);
         itemId3 = item3.getItemId();
        MenuItem item4 = binding.bottomNavigationView.getMenu().findItem(R.id.library);
         itemId4 = item4.getItemId();

        binding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                long itemId = item.getItemId();
                if (itemId == itemId1) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (itemId == itemId2) {
                    replaceFragment(new ShortsFragment());
                    return true;
                } else if (itemId == itemId3) {
                    replaceFragment(new SubscriptionFragment());
                    return true;
                } else if (itemId == itemId4) {
                    replaceFragment(new LibraryFragment());
                    return true;
                }
                return false;
            }
        });
        // Thay thế Fragment mặc định bằng HomeFragment
        replaceFragment(new HomeFragment());

        // Đặt hình nền cho bottom navigation view
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                long itemId = item.getItemId();
                if (itemId == itemId1) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (itemId == itemId2) {
                    replaceFragment(new ShortsFragment());
                    return true;
                } else if (itemId == itemId3) {
                    replaceFragment(new SubscriptionFragment());
                    return true;
                } else if (itemId == itemId4) {
                    replaceFragment(new LibraryFragment());
                    return true;
                }
                return false;
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_addsanpham_layout);

        LinearLayout ln_themsanpham = dialog.findViewById(R.id.layout_themsanpham);
        LinearLayout ln_ditoigiohang = dialog.findViewById(R.id.layoutditoigiohang);
        LinearLayout ln_ditoidonhang = dialog.findViewById(R.id.layoutdonhang);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        ln_themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ThemSanPham.class));
            }
        });
        ln_ditoigiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GioHangActivity.class));
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Đi tơi giỏ hàng", Toast.LENGTH_SHORT).show();

            }
        });

        ln_ditoidonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GioHangActivity.class));
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Đi tới đơn hàng", Toast.LENGTH_SHORT).show();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}