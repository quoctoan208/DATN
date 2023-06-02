package com.example.datn.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Adapter.TheLoaiSPAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.GUI.DonHangActivity;
import com.example.datn.GUI.GioHangActivity;
import com.example.datn.GUI.TimKiem_Activity;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment  extends Fragment {

    RecyclerView tlSanPhamRecycler, spItemRecycler;
    SanPhamAdapter sanPhamAdapter;
    TheLoaiSPAdapter theLoaiSPAdapter;
    Button button,btn_addsp;
    TextView txt_tatca, txt_tk;
    public static String MASP;

    public static List<TheLoai> listTL = new ArrayList<>();
    TheLoai a =  new TheLoai();

    private BottomSheetDialog bottomSheetDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        loadData();
        onclick();
    }

    private void onclick() {

        txt_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        txt_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TimKiem_Activity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.setContentView(R.layout.bottom_chon);
                TextView txt_giohang = bottomSheetDialog.findViewById(R.id.txt_giohang);
                TextView txt_donhang = bottomSheetDialog.findViewById(R.id.txt_donhang);
                txt_giohang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), GioHangActivity.class));
                        bottomSheetDialog.dismiss();
                    }
                });
                txt_donhang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), DonHangActivity.class));
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
            }
        });
    }

    private void loadData() {

        //Lấy dữ liệu sản phẩm
        APIService.apiService.getSanPham().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.body() != null) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    spItemRecycler.setLayoutManager(layoutManager);
                    sanPhamAdapter = new SanPhamAdapter(getActivity(), response.body());
                    spItemRecycler.setAdapter(sanPhamAdapter);
                } else {
                    Toast.makeText(getActivity(), "không co dl", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Lấy dữ liệu thể loại sản phẩm
        APIService.apiService.getTheLoai().enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                if (response.body() != null) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    tlSanPhamRecycler.setLayoutManager(layoutManager);
                    theLoaiSPAdapter = new TheLoaiSPAdapter(getActivity(), response.body(), spItemRecycler);
                    tlSanPhamRecycler.setAdapter(theLoaiSPAdapter);
                    for (TheLoai theLoai : response.body()){
                        TheLoai a =  new TheLoai(theLoai.getMaTL(),theLoai.getTenTL());
                        listTL.add(a);
                    }



                } else {
                    Toast.makeText(getActivity(), "Deo co dl", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void anhxa(View view) {
        tlSanPhamRecycler = view.findViewById(R.id.tl_recycler);
        spItemRecycler = view.findViewById(R.id.sanpham_recycler);
        button = view.findViewById(R.id.button);
        txt_tatca = view.findViewById(R.id.txt_tatca);
        txt_tk = view.findViewById(R.id.txt_tk);
//        bottomSheetDialog = new BottomSheetDialog(getActivity());
        btn_addsp = view.findViewById(R.id.button);
    }
}
