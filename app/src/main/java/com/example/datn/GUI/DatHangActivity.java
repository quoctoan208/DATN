package com.example.datn.GUI;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.GioHangFragment.sanPhamThanhToanList;
import static com.example.datn.Fragment.GioHangFragment.tongTienGioHang;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.Adapter.DatHangAdapter;
import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.ChiTietDonHang;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TaiKhoan;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText txt_hoten, txt_sdt, txt_diachi, txt_ghichu;
    private RecyclerView list_item;
    private TextView txt_tongtienthanhtoan;
    private Button btn_tt;
    int maSVBAN = 0;
    static List<GioHang> gioHangList;
    static List<GioHang> gioHangList1 = new ArrayList<>();

    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 9;
    float tongtien = 0;
    String maDH;


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
                SuKien.showDialog(DatHangActivity.this);
                if (txt_diachi.getText().toString().isEmpty() || txt_sdt.getText().toString().isEmpty()
                        || txt_hoten.getText().toString().isEmpty()) {
                    SuKien.dismissDialog();
                    Toast.makeText(DatHangActivity.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    addonhang();
                }
            }
        });
    }

    public static String generateRandomString() {
        char[] charArray = CHARACTERS.toCharArray();
        Random random = new Random();
        for (int i = charArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = charArray[i];
            charArray[i] = charArray[j];
            charArray[j] = temp;
        }
        return new String(charArray, 0, LENGTH);
    }

    public void addonhang() {

        String a = "Nhận hàng thanh toán";
        Date date = new Date();
        Timestamp ngayGiaoDich = new Timestamp(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ngayGiaoDichStr = sdf.format(ngayGiaoDich);
        String randomString = generateRandomString();
        maDH = "DH" + randomString;
        int SDT = Integer.parseInt(txt_sdt.getText().toString());
        //Tạo đơn hàng
        DonHang donHang = new DonHang(txt_diachi.getText().toString(), a, tongTienGioHang, 0, maSV, maSVBAN,
                maDH, ngayGiaoDichStr, SDT, txt_hoten.getText().toString(), txt_ghichu.getText().toString());
        APIService.apiService.PostDONHANG(donHang).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.isSuccessful()) {
                    addCTDH(sanPhamThanhToanList);
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                Log.d("Loi: ", t.getMessage());
                SuKien.dismissDialog();
            }
        });
    }

    private void addCTDH(List<SanPham> sanPhamList) {

        for (int i = 0; i < sanPhamList.size(); i++) {
            String randomString = generateRandomString();

            ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
            chiTietDonHang.setId(randomString);
            chiTietDonHang.setSoLuong(sanPhamThanhToanList.get(i).getSoLuong());
            chiTietDonHang.setTongTienThanhToan(tongTienGioHang);
            chiTietDonHang.setMaSP(sanPhamThanhToanList.get(i).getMaSP());
            chiTietDonHang.setMaDH(maDH);
            APIService.apiService.PostCHITIETDONHANG(chiTietDonHang).enqueue(new Callback<ChiTietDonHang>() {
                @Override
                public void onResponse(Call<ChiTietDonHang> call, Response<ChiTietDonHang> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DatHangActivity.this, "Thêm chi tiết DH thành công", Toast.LENGTH_SHORT).show();
                        xoaGioHang(sanPhamList);
                    }
                }

                @Override
                public void onFailure(Call<ChiTietDonHang> call, Throwable t) {
                    Toast.makeText(DatHangActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void xoaGioHang(List<SanPham> sanPhamList) {
        //tìm giỏ hàng của sinh viên XXX
        APIService.apiService.Getgiohang(maSV).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    //Duyệt tìm sản phẩm ở trong giỏ
                    for (SanPham sanPham : sanPhamList) {
                        //tìm thấy sản phẩm thì xóa sản phẩm trong giỏ hàng
                        APIService.apiService.GetgiohangbyMaSP(maSV,sanPham.getMaSP()).enqueue(new Callback<List<GioHang>>() {
                            @Override
                            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                                if (response.isSuccessful()) {
                                    //nếu tìm thấy thì xóa sản phẩm
                                    APIService.apiService.DeleteGIOHANG(response.body().get(0).getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                        @Override
                                        public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(DatHangActivity.this, "Đã xóa sản phẩm " + sanPham.getTenSP() + " trong giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d("TAG", "Chưa xóa được sản phẩm trong giỏ");
                                                Toast.makeText(DatHangActivity.this, "Chưa xóa được sản phẩm trong giỏ", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GioHang> call, Throwable t) {
                                            Toast.makeText(DatHangActivity.this, "Lỗi xóa giỏ hàng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("TAG", "Lỗi xóa giỏ hàng: " + t.getMessage());
                                        }
                                    });
                                } else {
                                    Log.d("TAG", "Không tìm thấy sản pham " + sanPham.getTenSP() + " trong giỏ hàng của bạn");
                                    Toast.makeText(DatHangActivity.this, "Không tìm thấy sản pham " + sanPham.getTenSP() + " trong giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                                Log.d("TAG", "Lỗi tìm giỏ hàng sản phẩm: " + t.getMessage());
                                Toast.makeText(DatHangActivity.this, "Lỗi tìm giỏ hàng sản phẩm: MaSV:"+maSV+ " - " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Log.d("TAG", "Không tìm thấy giỏ hàng của bạn");
                    Toast.makeText(DatHangActivity.this, "Không tìm thấy giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                SuKien.dismissDialog();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
        SuKien.dismissDialog();
        startActivity(new Intent(DatHangActivity.this, MainActivity.class));
        finish();

    }

    private void getdata_dathang() {
        APIService.apiService.GetTaikhoan(maSV).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if (response.isSuccessful()) {
                    txt_hoten.setText(response.body().getHoVaTen());
                    txt_sdt.setText("0" + response.body().getsDT());
                    txt_diachi.setText(response.body().getDiaChi());
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                SuKien.dismissDialog();
            }
        });

        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(DatHangActivity.this, sanPhamThanhToanList);
        list_item.setLayoutManager(new LinearLayoutManager(DatHangActivity.this, RecyclerView.VERTICAL, false));
        list_item.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();
        txt_tongtienthanhtoan.setText(formatter.format(tongTienGioHang) + " VND");
        getNguoiBan(sanPhamThanhToanList);
        SuKien.dismissDialog();

    }

    private void getNguoiBan(List<SanPham> sanPhamList) {
        for (SanPham sanPham : sanPhamList) {
            maSVBAN = sanPham.getMaSV();
        }
    }

    private void anhxa() {
        gioHangList = new ArrayList<>();
        SuKien.showDialog(this);
        img_back = findViewById(R.id.img_back);
        txt_hoten = findViewById(R.id.txt_hoten_tt);
        txt_sdt = findViewById(R.id.txt_sdt_tt);
        txt_diachi = findViewById(R.id.txt_diachi_tt);
        txt_ghichu = findViewById(R.id.txt_ghichu_tt);
        txt_tongtienthanhtoan = findViewById(R.id.txt_ttt);
        list_item = findViewById(R.id.list_item_thanhtoan);
        btn_tt = findViewById(R.id.btn_tt);
    }
}