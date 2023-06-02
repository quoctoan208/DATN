package com.example.datn.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        tblayout.setTabTextColors(this.getResources().getColor(R.color.black),this.getResources().getColor(R.color.teal_200));
        new TabLayoutMediator(tblayout, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chờ xác nhận ");
                        break;
                    case 1:
                        tab.setText("Đang giao");
                        break;
                    case 2:
                        tab.setText("Đã giao");
                        break;
                    case 3:
                        tab.setText("Đã hủy");
                        break;
                    default:
                        break;

                }
            }
        }).attach();
    }
}