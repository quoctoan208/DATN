package com.example.datn.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datn.Adapter.Donhang_viewPage;
import com.example.datn.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DonHangActivity extends AppCompatActivity {
    private ImageView img_back;
    private TabLayout tblayout;
    private ViewPager2 viewpager;
    private Donhang_viewPage donhang_viewPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        anhxa();
        onclick();
    }

    private void onclick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void anhxa() {
        img_back=findViewById(R.id.img_back);
        tblayout=findViewById(R.id.tblayout);
        viewpager=findViewById(R.id.viewpager);
        donhang_viewPage=new Donhang_viewPage(this);
        viewpager.setAdapter(donhang_viewPage);
        tblayout.setTabTextColors(ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.teal_200));
        String[] tabTitles = {"Chờ xác nhận","Chờ Gửi Hàng","Đang Giao hàng", "Đã nhận hàng", "Đã hủy"};

        new TabLayoutMediator(tblayout, viewpager, (tab, position) -> {
            tab.setText(tabTitles[position]);
        }).attach();
    }
}