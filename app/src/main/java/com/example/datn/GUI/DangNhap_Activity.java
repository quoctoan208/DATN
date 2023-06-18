package com.example.datn.GUI;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.TaiKhoan;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap_Activity extends AppCompatActivity {
    private EditText txt_masvdangnhap, txt_password;

    public static TaiKhoan taikhoancuatoi;
    private Button btn_login;
    public static int maSV, MALOP = 101181;//10118369

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        onclick();

    }

    private void onclick() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuKien.showDialog(DangNhap_Activity.this);
                Dangnhap();
            }
        });
    }

    private void Dangnhap() {
        String masv = txt_masvdangnhap.getText().toString();
        String password = txt_password.getText().toString();
        if (password.isEmpty() || masv.isEmpty() ) {
            SuKien.dismissDialog();
            Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!masv.matches("\\d+")) {
            SuKien.dismissDialog();
            Toast.makeText(this, "Mã sinh viên sai cú pháp", Toast.LENGTH_SHORT).show();
        } else {
            int masv1 = Integer.parseInt(txt_masvdangnhap.getText().toString());
            APIService.apiService.Kiemtra(masv1, password).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        maSV = masv1;
                        APIService.apiService.GetTaikhoan(masv1).enqueue(new Callback<TaiKhoan>() {
                            @Override
                            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                                if (response.isSuccessful()) {
                                    taikhoancuatoi = response.body();
                                }
                            }

                            @Override
                            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                SuKien.dismissDialog();
                                Toast.makeText(DangNhap_Activity.this, "không lấy đợc dữ liệu tài khoản", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(DangNhap_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        SuKien.dismissDialog();
                        startActivity(new Intent(DangNhap_Activity.this, MainActivity.class));
                    } else {
                        SuKien.dismissDialog();
                        Toast.makeText(DangNhap_Activity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }SuKien.dismissDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(DangNhap_Activity.this, "out", Toast.LENGTH_SHORT).show();
                    SuKien.dismissDialog();
                }
            });
        }
    }


    private void anhxa() {
        taikhoancuatoi = new TaiKhoan();
        txt_masvdangnhap = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
    }
}