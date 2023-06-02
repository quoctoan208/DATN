package com.example.datn.GUI;

import static com.example.datn.BUS.SuKien.LOATDING;
import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.GUI.DangNhap_Activity.MASINHVIEN;
import static com.example.datn.GUI.GioHangActivity.getdata;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.DatHangAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.NguoiDung;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText txt_masv, txt_sdt, txt_diachi, txt_ghichu;
    private RecyclerView list_item;
    private TextView txt_ttt;
    private Button btn_tt;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        anhxa();
        getdata_dathang();
        onclick();
    }

    private void onclick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOATDING(dialog);
                if (txt_diachi.getText().toString().isEmpty()||txt_sdt.getText().toString().isEmpty()){
                    dialog.dismiss();
                    Toast.makeText(DatHangActivity.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    addonhang();
                }
            }
        });
    }

    public void addonhang(){
        APIService.apiService.Getgiohang(MASINHVIEN).enqueue(new Callback<List<GioHang>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    for (GioHang gioHang:response.body()){
                        DonHang donHang=new DonHang(0,gioHang.getSoLuong(),gioHang.getTongTien(),0, MASINHVIEN,gioHang.getMaSP(),txt_diachi.getText().toString().trim(),
                                txt_sdt.getText().toString(),txt_ghichu.getText().toString(),java.time.LocalDateTime.now() + "");
                        APIService.apiService.PostDONHANG(donHang).enqueue(new Callback<DonHang>() {
                            @Override
                            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                                APIService.apiService.DeleteGIOHANG(gioHang.getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                    @Override
                                    public void onResponse(Call<GioHang> call, Response<GioHang> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<GioHang> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<DonHang> call, Throwable t) {

                            }
                        });
                    }
                    Toast.makeText(DatHangActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    getdata();
                    dialog.dismiss();
                    onBackPressed();
                }

            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }
    private void getdata_dathang() {
        APIService.apiService.GetNGUOIDUNGDONHANG(MASINHVIEN).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (response.isSuccessful()){
                    NguoiDung nguoiDung=response.body().get(0);
                    txt_masv.setText(nguoiDung.getMaSV());
                    txt_sdt.setText(nguoiDung.getsDT());
                    txt_diachi.setText(nguoiDung.getDiaChi());
                }
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {

            }
        });
        APIService.apiService.Getgiohang(MASINHVIEN).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    DatHangAdapter datHangAdapter = new DatHangAdapter(response.body(), DatHangActivity.this);
                    list_item.setLayoutManager(new LinearLayoutManager(DatHangActivity.this, RecyclerView.VERTICAL, false));
                    list_item.setAdapter(datHangAdapter);
                    int tongtien = 0;
                    for (GioHang gioHang : response.body()) {
                        tongtien = tongtien + gioHang.getTongTien();
                        txt_ttt.setText(formatter.format(tongtien) + " VND");
                    }
                }

            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        dialog=new Dialog(this);
        img_back = findViewById(R.id.img_back);
        txt_masv = findViewById(R.id.txt_hoten);
        txt_sdt = findViewById(R.id.txt_sdt);
        txt_diachi = findViewById(R.id.txt_diachi);
        txt_ghichu = findViewById(R.id.txt_ghichu);
        txt_ttt = findViewById(R.id.txt_ttt);
        list_item = findViewById(R.id.list_item);
        btn_tt = findViewById(R.id.btn_tt);
    }
}