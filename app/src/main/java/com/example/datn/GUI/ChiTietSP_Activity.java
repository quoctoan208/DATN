package com.example.datn.GUI;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.HomeFragment.MASP;
import static com.example.datn.GUI.DangNhap_Activity.MALOP;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.AnhSP;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSP_Activity extends AppCompatActivity {
    private ImageView img_nhantin, imageView;
    private Button btn_chitiet_themgiohang;
    private TextView chitiet_malop, chitiet_soluong, chitiet_tensp, chitiet_mota, chitiet_dongia;
    private float tongtien;
    private List<SlideModel> imageList;
    private ImageSlider imageSlider;
    public static String maSV_SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        anhxa();
        getdata();
        onclick();
    }

    private void onclick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_chitiet_themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGioHang();
            }
        });
        img_nhantin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietSP_Activity.this, NhanTinActivity.class));
            }
        });

        chitiet_malop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietSP_Activity.this, ProfNguoiBanActivity.class));
            }
        });
    }


    private void addGioHang() {
        SuKien.showDialog(ChiTietSP_Activity.this);
        long a = (long) tongtien;
        int b = (int) a; // ép kiểu in
        GioHang gioHang = new GioHang(0, maSV, MASP, 1, b);
        APIService.apiService.PostGIOHANG(gioHang).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    SuKien.dismissDialog();
                    Toast.makeText(ChiTietSP_Activity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    SuKien.dismissDialog();
                    onBackPressed();
                    Toast.makeText(ChiTietSP_Activity.this, "Lỗi: Giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                SuKien.dismissDialog();
            }
        });
    }


    private void getdata() {
        APIService.apiService.GetSANPHAM(MASP).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {

                getdataAnhSP();
                chitiet_tensp.setText(response.body().getTenSP());
                chitiet_mota.setText(response.body().getMatoSP());
                chitiet_dongia.setText(formatter.format(response.body().getDonGia()) + " VND");
                chitiet_soluong.setText("Số Lượng: " + response.body().getSoLuong());
                chitiet_malop.setText("Người bán:" + response.body().getMaSV());
                maSV_SP = response.body().getMaSV() + "";
                tongtien = response.body().getDonGia();
                SuKien.dismissDialog();
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(ChiTietSP_Activity.this, "Lỗi: Không lấy được thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                SuKien.dismissDialog();
            }
        });
    }

    private void getdataAnhSP() {
        APIService.apiService.GetAnhSP(MASP).enqueue(new Callback<AnhSP>() {
            @Override
            public void onResponse(Call<AnhSP> call, Response<AnhSP> response) {

                imageList = new ArrayList<>(); // Create image list
                imageList.add(new SlideModel(response.body().getAnh1(), ScaleTypes.FIT));
                imageList.add(new SlideModel(response.body().getAnh2(), ScaleTypes.FIT));
                imageList.add(new SlideModel(response.body().getAnh3(), ScaleTypes.FIT));
                imageList.add(new SlideModel(response.body().getAnh4(), ScaleTypes.FIT));

                imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);

            }

            @Override
            public void onFailure(Call<AnhSP> call, Throwable t) {
                SuKien.dismissDialog();
                Toast.makeText(ChiTietSP_Activity.this, "Lỗi: Không load được ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        SuKien.showDialog(this);
        btn_chitiet_themgiohang = findViewById(R.id.btn_chitiet_themgiohang);
        chitiet_tensp = findViewById(R.id.chitiet_tensp);
        chitiet_mota = findViewById(R.id.chitiet_mota);
        chitiet_dongia = findViewById(R.id.chitiet_dongia);
        imageView = findViewById(R.id.imageView);
        imageSlider = findViewById(R.id.image_slider);
        chitiet_malop = findViewById(R.id.chitiet_masinhvien);
        chitiet_soluong = findViewById(R.id.chitiet_soluong);
        img_nhantin = findViewById(R.id.imagenhantin);
    }
}