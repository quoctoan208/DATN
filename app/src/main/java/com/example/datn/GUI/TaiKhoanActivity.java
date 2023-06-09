package com.example.datn.GUI;

import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.os.Bundle;
import android.util.Log;
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
    TextView hoTen, hoTen2, masv, maLop, sDT, diaChi;
    Button btnSuaTk;

    public static TaiKhoan taiKhoan ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincuatoi);
        anhxa();
        getDataUser();
        onclick();
    }

    private void onclick() {
    }

    private void getDataUser() {
        APIService.apiService.GetTaikhoan(maSV).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                taiKhoan = response.body();
                hoTen.setText(response.body().getHoVaTen());
                hoTen2.setText(response.body().getHoVaTen());
                masv.setText(""+response.body().getMaSV());
                maLop.setText(""+response.body().getMaLop());
                sDT.setText("0"+response.body().getsDT());
                diaChi.setText(response.body().getDiaChi());
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        taiKhoan = new TaiKhoan();
        btnSuaTk = findViewById(R.id.btn_suataikhoan);
        hoTen = findViewById(R.id.tv_hoten_tk);
        hoTen2 = findViewById(R.id.tv_hoten2_tk);
        masv = findViewById(R.id.tv_masv_tk);
        maLop = findViewById(R.id.tv_malop_tk);
        sDT = findViewById(R.id.tv_sdt_tk);
        diaChi = findViewById(R.id.tv_diachi_tk);
    }
}
