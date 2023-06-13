package com.example.datn.Fragment;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.GioHangAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.GUI.DatHangActivity;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GioHangFragment extends Fragment {

    public static ImageView img_back;
    public static RecyclerView list_item;
    public static TextView txt_tongtien;
    public static int tongTienGioHang ;
    public static TextView txt_error;
    public static Button btn_mua_hang;
    public static LinearLayout layout;
    static GioHangAdapter gio_hangs ;
    static List<GioHang> gioHangList = new ArrayList<>();
    public static List<SanPham> sanPhamThanhToanList;
    boolean check = true;
    static Animation animation;
    private static Activity gioHangFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_gio_hang, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        gioHangFragment = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        getdata();
        tinhTienGioHang();
        onClick();
    }

    public static void tinhTienGioHang() {
        animation = AnimationUtils.loadAnimation(gioHangFragment,
                R.anim.slide_down);
        //tính tổng tiền của giỏ thông qua các sp đã được check
        if (sanPhamThanhToanList.size() == 0){
            tongTienGioHang = 0;
            txt_tongtien.setText(formatter.format(tongTienGioHang) + "");
            txt_tongtien.startAnimation(animation);
        }else {
            tongTienGioHang = 0;
            for(SanPham sanPham:sanPhamThanhToanList){
                if(sanPham == null){
                    txt_tongtien.setText(formatter.format(tongTienGioHang) + " VNĐ");
                    txt_tongtien.startAnimation(animation);
                }else {
                    tongTienGioHang += (sanPham.getSoLuong() * sanPham.getDonGia());
                    txt_tongtien.setText(formatter.format(tongTienGioHang) + " VNĐ");
                    txt_tongtien.startAnimation(animation);
                }

            }
        }
    }

    private void onClick() {
        btn_mua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sanPhamThanhToanList.size() == 0){
                    Toast.makeText(getContext(), "Chưa sản phẩm nào được chọn", Toast.LENGTH_SHORT).show();
                }
                else if (sanPhamThanhToanList.size() == 1){
                    startActivity(new Intent(getContext(), DatHangActivity.class));
                }
                else {
                    check = true;
                    for (int i = 1; i < sanPhamThanhToanList.size(); i++) {
                        if (sanPhamThanhToanList.get(i).getMaSV() != sanPhamThanhToanList.get(i - 1).getMaSV()) {
                            check = false; // Nếu phát hiện phần tử khác giá trị đầu tiên, đặt biến check thành false
                            break; // Kết thúc vòng lặp để kiểm tra tiếp
                        }
                    }
                    if (check) {
                        startActivity(new Intent(getContext(), DatHangActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Hãy chọn các sản phẩm đặt hàng cùng 1 người bán!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public static void getdata() {
        // Gọi API để lấy danh sách giỏ hàng của sinh viên dựa trên mã số sinh viên
        APIService.apiService.Getgiohang(maSV).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    gioHangList = new ArrayList<>();
                    gioHangList.addAll(response.body());
                    txt_error.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    gio_hangs.notifyDataSetChanged();
                } else {
                    SuKien.dismissDialog();
                    txt_error.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                SuKien.dismissDialog();
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
        setLayout();
    }

    public static void setLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(gioHangFragment,RecyclerView.VERTICAL,false);
        list_item.setLayoutManager(linearLayoutManager);
        gio_hangs = new GioHangAdapter(gioHangList,gioHangFragment);
        list_item.setAdapter(gio_hangs);
    }

    private void anhxa(View view) {
        sanPhamThanhToanList = new ArrayList<>();
        list_item = view.findViewById(R.id.list_item);
        txt_tongtien = view.findViewById(R.id.txt_tongtien);
        btn_mua_hang = view.findViewById(R.id.btn_mua_hang);
        txt_error = view.findViewById(R.id.txt_error);
        layout = view.findViewById(R.id.layout);
        img_back = view.findViewById(R.id.img_back);
    }
}