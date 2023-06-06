package com.example.datn.Fragment;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.datn.GUI.DatHangActivity;
import com.example.datn.GUI.GioHangActivity;
import com.example.datn.Model.GioHang;
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
    public static TextView txt_error;
    public static Button btn_mua_hang;
    public static LinearLayout layout;
    static GioHangAdapter gio_hangs ;
    static List<GioHang> gioHangList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_gio_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        getdata();
        setLayout();
        onClick();
    }
    private void onClick() {
        btn_mua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DatHangActivity.class));
            }
        });
    }

    public void getdata() {
        // Gọi API để lấy danh sách giỏ hàng của sinh viên dựa trên mã số sinh viên
        APIService.apiService.Getgiohang(maSV).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
                    gioHangList = new ArrayList<>();
                    txt_error.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    int tongtien=0;
                    for (GioHang gioHang : response.body()) {
                        tongtien = tongtien + gioHang.getTongTien();
                        GioHang gioHang1 = new GioHang(gioHang.getIDGIOHANG(),gioHang.getMaSV()
                                ,gioHang.getMaSP(),gioHang.getSoLuong(),gioHang.getTongTien());
                        txt_tongtien.setText(formatter.format(tongtien) + " VND");
                        gioHangList.add(gioHang1);
                        gio_hangs.notifyDataSetChanged();
                    }

                } else {
                    txt_error.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                Toast.makeText(getContext(), "Không tải được giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        list_item.setLayoutManager(linearLayoutManager);
        gio_hangs = new GioHangAdapter(gioHangList,getActivity());
        list_item.setAdapter(gio_hangs);
    }

    private void anhxa(View view) {
        list_item = view.findViewById(R.id.list_item);
        txt_tongtien = view.findViewById(R.id.txt_tongtien);
        btn_mua_hang = view.findViewById(R.id.btn_mua_hang);
        txt_error = view.findViewById(R.id.txt_error);
        layout = view.findViewById(R.id.layout);
        img_back = view.findViewById(R.id.img_back);
    }
}