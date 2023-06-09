package com.example.datn.GUI;


import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.ChiTietDonHangAdapter;
import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.ChiTietDonHang;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    static SanPhamAdapter sanPhamAdapter;
    public static List<SanPham> sanPhamList;
    Button btn_cance;
    ImageView img;

    TextView txt_ten, txt_sdt, txt_diachi, txt_phuongthucthanhtoan, txt_madh, txt_ngaygiaodich , txt_trangthaidonhang, txt_nhabanhang;

    TextView tongTienCTDH;

    public static String maDHChon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);
        anhxa();
        setUpLayout();
        getdata();
        onclick();
    }


    private void setUpLayout() {
        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(this,sanPhamList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sanPhamAdapter);
    }

    private void onclick() {
        btn_cance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietDonHangActivity.this, ChiTietDonHangActivity.class));
            }
        });
    }

    private void getdata() {
        APIService.apiService.GetDonHangid(maDHChon).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if(response.isSuccessful()){
                    txt_ten.setText(response.body().getHoVaTen());
                    txt_sdt.setText("0"+response.body().getSDT());
                    txt_diachi.setText(response.body().getDiaChiGiaoHang());
                    tongTienCTDH.setText(response.body().getTongTienThanhToan()+" VNĐ");
                    txt_phuongthucthanhtoan.setText(response.body().getPhuongThucThanhToan());
                    txt_madh.setText(response.body().getMaDH());
                    txt_ngaygiaodich.setText(response.body().getNgayGiaoDich());
                    int x = response.body().getTrangThaiDH();
                    if (maSV == response.body().getMaSVBan()){
                        txt_nhabanhang.setText("Shop của tôi: "+maSV);
                    }else if (maSV == response.body().getMaSVMua()) {
                        txt_nhabanhang.setText("Nhà bán hàng: "+response.body().getMaSVBan());
                    } else if (x == 0) {
                        txt_trangthaidonhang.setText("Đơn hàng đamg chờ được xác nhận !");
                    } else if (x == 1) {
                        txt_trangthaidonhang.setText("Đơn hàng đamg chờ để gửi cho đơn vị vận chuyển !");
                    } else if (x == 2) {
                        txt_trangthaidonhang.setText("Đơn hàng đã được giao cho đơn vị vận chuyển !");
                    } else if (x == 3) {
                        txt_trangthaidonhang.setText("Đơn hàng đã được xác nhận giao thành công!");
                    } else if (x == 4) {
                        txt_trangthaidonhang.setText("Đơn hàng đã hủy!");
                    }

                }
                else {
                    SuKien.dismissDialog();
                    Toast.makeText(ChiTietDonHangActivity.this, "Đơn hàng trống !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                SuKien.dismissDialog();
                Toast.makeText(ChiTietDonHangActivity.this, "Không lấy được dữ liệu đơn hàng !", Toast.LENGTH_SHORT).show();
            }
        });


        APIService.apiService.getChiTietDonHang(maDHChon).enqueue(new Callback<List<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHang>> call, Response<List<ChiTietDonHang>> response) {
                if (response.body() != null){
                    for(ChiTietDonHang chiTietDonHang : response.body()){
                        //call api get SP by MSP
                        String maSP = chiTietDonHang.getMaSP();
                        APIService.apiService.GetSANPHAM(maSP).enqueue(new Callback<SanPham>() {
                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                SanPham sanPham = response.body();
                                sanPhamList.add(sanPham);
                                setSanPham(sanPhamList);
                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {
                                SuKien.dismissDialog();
                                Toast.makeText(ChiTietDonHangActivity.this, "Không laod được sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHang>> call, Throwable t) {
                SuKien.dismissDialog();
                Toast.makeText(ChiTietDonHangActivity.this, "Không load được CTDH", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setSanPham(List<SanPham> sanPhams) {
        SuKien.dismissDialog();
        sanPhamAdapter.setData(sanPhams);
    }

    private void anhxa() {
        SuKien.showDialog(this);
        recyclerView = findViewById(R.id.list_ctdh_item);
        tongTienCTDH = findViewById(R.id.txt_ctdh_tongtien);
        btn_cance = findViewById(R.id.btn_ctdh_cance);
        img = findViewById(R.id.img_ctdh_back);
        txt_ten = findViewById(R.id.txt_hotenCTDH);
        txt_sdt = findViewById(R.id.txt_sdtCTDH);
        txt_diachi = findViewById(R.id.txt_diachiCTDH);
        txt_phuongthucthanhtoan = findViewById(R.id.txt_phuongthucthanhtoanCTDH);
        txt_madh = findViewById(R.id.txt_madhCTDH);
        txt_ngaygiaodich = findViewById(R.id.txt_ngaygiaodichCTDH);
        txt_trangthaidonhang = findViewById(R.id.txt_chitiettrangthaiCTDH);
        txt_nhabanhang = findViewById(R.id.txt_chitietnhabanhang);

    }
}
