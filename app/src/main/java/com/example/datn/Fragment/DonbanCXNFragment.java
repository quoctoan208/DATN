package com.example.datn.Fragment;

//import static com.example.doan4.GUI.DangNhap_Activity.USENAME;

import static com.example.datn.GUI.ChiTietDonHangActivity.maDHChon;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.datn.GUI.DonHangBanActivity;
import com.example.datn.Model.DonHang;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonbanCXNFragment extends Fragment {
    public static RecyclerView recyclerView;
    public static LinearLayout linearLayout;
    public static DonHangBanActivity donHangBanActivity;

    static List<DonHang> donHangList;
    static DonHangDaBanAdapter donHangDaBanAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donban_c_x_n, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        setUpViewCNXBAN();
        getdata_CXNDONBAN();
        onClick();
    }

    private void onClick() {
        donHangDaBanAdapter.setOnClickListener(new DonHangDaBanAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                DonHang donHang = donHangList.get(pos);
                maDHChon = donHang.getMaDH();
                startActivity(new Intent(getContext(), ChiTietDonHangActivity.class));
            }
        });
    }

    public static void setUpViewCNXBAN() {
        donHangList = new ArrayList<>();
        donHangDaBanAdapter = new DonHangDaBanAdapter(donHangList, donHangBanActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(donHangBanActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(donHangDaBanAdapter);
    }

    public static void getdata_CXNDONBAN() {
        APIService.apiService.GetalldonhangBan(maSV, 0).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.isSuccessful()) {
                    donHangList.addAll(response.body());
                    Log.d("TAG", "onResponse: "+donHangList.size());
                    donHangDaBanAdapter.setData(donHangList);
                    linearLayout.setVisibility(View.GONE);
                }
                else {
                    Log.e("CHECK", "Response error: " + response.code() + " - " + response.message());
                    Toast.makeText(donHangBanActivity.getApplication(), "Không có đơn hàng", Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                linearLayout.setVisibility(View.VISIBLE);
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void anhxa(View view) {
        donHangBanActivity = (DonHangBanActivity) getActivity();
        recyclerView = view.findViewById(R.id.RecyclerViewCXNDONBAN);
        linearLayout = view.findViewById(R.id.LinearLayoutCXNDONBAN);
    }
}