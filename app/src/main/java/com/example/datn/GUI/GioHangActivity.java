package com.example.datn.GUI;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.GioHangAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.Model.GioHang;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {
    public static ImageView img_back;
    public static RecyclerView list_item;
    public static TextView txt_tongtien;
    public static TextView txt_error;
    public static Button btn_mua_hang;
    public static LinearLayout layout;
    static GioHangAdapter gio_hangs;
    static List<GioHang> gioHangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        getdata();
        setLayout();
        onClick();
    }

    private void onClick() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_mua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHangActivity.this,DatHangActivity.class));
            }
        });
    }

    public static void getdata() {
        // Gọi API để lấy danh sách giỏ hàng của sinh viên dựa trên mã số sinh viên
        APIService.apiService.Getgiohang(maSV).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    txt_error.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    int tongtien=0;
                    for (GioHang gioHang : response.body()) {
                        tongtien = tongtien + gioHang.getTongTien();
                        GioHang gioHang1 = new GioHang(gioHang.getIDGIOHANG(),gioHang.getMaSV()
                                ,gioHang.getMaSP(),gioHang.getSoLuong(),gioHang.getTongTien());
                        gioHangList.add(gioHang1);
                        gio_hangs.notifyDataSetChanged();
                        txt_tongtien.setText(formatter.format(tongtien) + " VND");
                    }


                } else {
                    txt_error.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }

    private void setLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        list_item.setLayoutManager(linearLayoutManager);
        gio_hangs = new GioHangAdapter(gioHangList,GioHangActivity.this);
        list_item.setAdapter(gio_hangs);
    }

    private void anhxa() {
        list_item = findViewById(R.id.list_item);
        txt_tongtien = findViewById(R.id.txt_tongtien);
        btn_mua_hang = findViewById(R.id.btn_mua_hang);
        txt_error = findViewById(R.id.txt_error);
        layout = findViewById(R.id.layout);
        img_back = findViewById(R.id.img_back);
    }
}