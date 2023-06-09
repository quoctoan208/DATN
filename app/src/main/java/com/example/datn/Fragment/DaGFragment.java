package com.example.datn.Fragment;

import static com.example.datn.Fragment.CXNFragment.donHangActivity;
import static com.example.datn.GUI.ChiTietDonHangActivity.maDHChon;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.DonHangAdapter;
import com.example.datn.Adapter.DonHangDaBanAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.GUI.ChiTietDonHangActivity;
import com.example.datn.GUI.DonHangActivity;
import com.example.datn.Model.DonHang;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaGFragment extends Fragment {
    static RecyclerView recyclerView;
    static LinearLayout linearLayout;
    static DonHangActivity donHangActivity;
    static DonHangAdapter donHangAdapter;
    static List<DonHang> donHangList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_da_g, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        setUpViewDaG();
        getdataDaG();
        onClick();
    }

    private void onClick() {

        donHangAdapter.setOnClickListener(new DonHangAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                DonHang donHang = donHangList.get(pos);
                maDHChon = donHang.getMaDH();
                startActivity(new Intent(getContext(), ChiTietDonHangActivity.class));
            }
        });

    }

    public static void setUpViewDaG() {
        donHangList = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(donHangList,donHangActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(donHangActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(donHangAdapter);
    }

    public static void getdataDaG() {
        APIService.apiService.Getalldonhangmua(maSV,3).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.isSuccessful()) {
                    linearLayout.setVisibility(View.GONE);
                    donHangList.addAll(response.body());
                    donHangAdapter.setData(donHangList);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                Toast.makeText(donHangActivity.getApplication(), "Không tìm thấy đơn Đã Giao", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa(View view) {
        recyclerView=view.findViewById(R.id.RecyclerViewDG);
        linearLayout=view.findViewById(R.id.LinearLayoutDG);
    }
}