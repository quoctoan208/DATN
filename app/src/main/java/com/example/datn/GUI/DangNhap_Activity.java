package com.example.datn.GUI;

import static com.example.datn.BUS.SuKien.LOATDING;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.Api.APIService;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.NguoiDung;
import com.example.datn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap_Activity extends AppCompatActivity {
    private EditText txt_masvdangnhap,txt_password;
    private Button btn_login;
    public static int MASINHVIEN = 10119736,MALOP = 101195;
    private Dialog dialog;
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
                LOATDING(dialog);
                Dangnhap();
            }
        });
    }

    private void Dangnhap() {
        int masv=Integer.parseInt(txt_masvdangnhap.getText().toString());
        String password=txt_password.getText().toString();
        if (password == ""){
            dialog.dismiss();
            Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {

            APIService.apiService.Kiemtra(masv,password).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                            MASINHVIEN =masv;
                            getUser();
                            Toast.makeText(DangNhap_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DangNhap_Activity.this, MainActivity.class));
                    }else {
                        dialog.dismiss();
                        Toast.makeText(DangNhap_Activity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(DangNhap_Activity.this, "out", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });
        }
    }

    private void getUser(){
        APIService.apiService.GetNGUOIDUNG(MASINHVIEN).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if(response.body() != null){
                    MALOP = response.body().getMaLop();
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        txt_masvdangnhap =findViewById(R.id.txt_email);
        txt_password=findViewById(R.id.txt_password);
        btn_login=findViewById(R.id.btn_login);
        dialog=new Dialog(this);
    }
}