package com.example.datn.Fragment;

import static com.example.datn.GUI.DangNhap_Activity.taikhoancuatoi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datn.GUI.DonHangActivity;
import com.example.datn.GUI.DonHangBanActivity;
import com.example.datn.GUI.TaiKhoanActivity;
import com.example.datn.R;

public class TaiKhoanFragment extends Fragment {

    TextView hoten;
    Button donhang, donhangban , thietlaptaikhoan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taikhoan, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        getData();
        onclick();
    }

    private void getData() {
        hoten.setText(taikhoancuatoi.getHoVaTen());
    }

    private void onclick() {
        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DonHangActivity.class));
            }
        });

        donhangban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DonHangBanActivity.class));
            }
        });

        thietlaptaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TaiKhoanActivity.class));
            }
        });
    }

    private void anhxa(View view) {
        donhang = view.findViewById(R.id.btnDonMua);
        donhangban = view.findViewById(R.id.btndonban);
        thietlaptaikhoan = view.findViewById(R.id.btnEditUser);
        hoten = view.findViewById(R.id.tv_hoTentaikhoan);
    }
}