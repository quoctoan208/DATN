package com.example.datn.GUI;

import static com.example.datn.GUI.DangNhap_Activity.maSV;
import static com.example.datn.GUI.DangNhap_Activity.taikhoancuatoi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.TaiKhoan;
import com.example.datn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanActivity extends AppCompatActivity {
    static TextView hoTen, hoTen2, masv, maLop, sDT, diaChi;
    Button btnSuaTk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincuatoi);
        anhxa();
        getDataUser();
        onclick();
    }

    private void onclick() {
        btnSuaTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaiKhoanActivity.this, SuaTaiKhoan.class));
            }
        });

    }

    public static void getDataUser() {

        hoTen.setText(taikhoancuatoi.getHoVaTen());
        hoTen2.setText(taikhoancuatoi.getHoVaTen());
        masv.setText(""+taikhoancuatoi.getMaSV());
        maLop.setText(""+taikhoancuatoi.getMaLop());
        sDT.setText("0"+taikhoancuatoi.getsDT());
        diaChi.setText(taikhoancuatoi.getDiaChi());

    }

    private void anhxa() {
        btnSuaTk = findViewById(R.id.btn_suataikhoan);
        hoTen = findViewById(R.id.tv_hoten_tk);
        hoTen2 = findViewById(R.id.tv_hoten2_tk);
        masv = findViewById(R.id.tv_masv_tk);
        maLop = findViewById(R.id.tv_malop_tk);
        sDT = findViewById(R.id.tv_sdt_tk);
        diaChi = findViewById(R.id.tv_diachi_tk);
    }
}
