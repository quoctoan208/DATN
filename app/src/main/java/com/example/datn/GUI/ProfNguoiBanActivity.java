package com.example.datn.GUI;

import static com.example.datn.GUI.ChiTietSP_Activity.maSV_SP;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TaiKhoan;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfNguoiBanActivity extends AppCompatActivity {

    TextView txt_hoten, txt_masv;
    static RecyclerView recyclerView;

    static List<SanPham> sanPhamList;

    static SanPhamAdapter sanPhamAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinshop);
        //setUpView();
        anhxa();
        getDataUser();
        onclick();
    }

    private void setUpView() {
        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(ProfNguoiBanActivity.this, sanPhamList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfNguoiBanActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sanPhamAdapter);
    }

    private void onclick() {
    }

    private void getDataUser() {
        int a = Integer.parseInt(maSV_SP);

        APIService.apiService.GetTaikhoan(a).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ProfNguoiBanActivity.this, "Không có người bán", Toast.LENGTH_SHORT).show();
                } else{
                    if(response.body().getHoVaTen().isEmpty()){
                        txt_hoten.setText("Người bán chưa cập nhật tên");
                    }else {
                        txt_hoten.setText(""+response.body().getHoVaTen());
                    }
                    txt_masv.setText("Mã SV: "+response.body().getMaSV());
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                SuKien.dismissDialog();
                Toast.makeText(ProfNguoiBanActivity.this, "Không tìm được sinh viên!", Toast.LENGTH_SHORT).show();
            }
        });

        APIService.apiService.getSanPhamcuatoi(a).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if(response.body()!= null){
                   for(SanPham sanPham : response.body()){
                       if(sanPham.getXetDuyet() == 2){
                           sanPhamList = new ArrayList<>();
                           sanPhamList.addAll(response.body());
                           sanPhamAdapter = new SanPhamAdapter(ProfNguoiBanActivity.this, sanPhamList);
                           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfNguoiBanActivity.this, LinearLayoutManager.VERTICAL, false);
                           recyclerView.setLayoutManager(linearLayoutManager);
                           recyclerView.setAdapter(sanPhamAdapter);
                           sanPhamAdapter.notifyDataSetChanged();
                           SuKien.dismissDialog();
                       }
                   }
                }
                else {
                    SuKien.dismissDialog();
                    Toast.makeText(ProfNguoiBanActivity.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                SuKien.dismissDialog();
                Toast.makeText(ProfNguoiBanActivity.this, "Lỗi không tìm được sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhxa() {
        SuKien.showDialog(this);
        recyclerView = findViewById(R.id.viewsanphamprof);
        txt_hoten = findViewById(R.id.tv_userFullName);
        txt_masv = findViewById(R.id.tv_msvien);
    }
}
