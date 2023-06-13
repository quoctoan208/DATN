package com.example.datn.Fragment;

import static com.example.datn.GUI.DangNhap_Activity.maSV;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Adapter.TheLoaiSPAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.GUI.ChiTietSP_Activity;
import com.example.datn.GUI.TimKiem_Activity;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView tlSanPhamRecycler, spItemRecycler;
    SanPhamAdapter sanPhamAdapter;
    TheLoaiSPAdapter theLoaiSPAdapter;
    TextView txt_tatca, txt_tk;
    public static String MASP;
    Dialog dialog;
    public static List<TheLoai> listTL;
    private ImageSlider imageSlider;
    private List<SlideModel> imageList;
    List<SanPham> sanPhamList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        setImageSlide();
        setUpView();
        loadData();
        onclick();
    }

    private void setImageSlide() {
        imageList = new ArrayList<>(); // Create image list
        imageList.add(new SlideModel("https://dean1665.vn/uploads/school/kt-hung-yen.jpg", ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel("https://photo-cms-giaoducthoidai.epicdn.me/w820/Uploaded/2023/usvzfouipohevboheuehnbjmdpn/2023_05_25/hon-2000-vi-tri-viec-lam-cho-sinh-vien-truong-utehy-2-2817.jpg", ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel("https://dean1665.vn/uploads/school/kt-hung-yen.jpg", ScaleTypes.CENTER_INSIDE));

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);
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

        theLoaiSPAdapter.setOnClickListener(new TheLoaiSPAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                SuKien.showDialog(getContext());
                TheLoai theLoai = listTL.get(pos);
                sanPhamList.clear();
                APIService.apiService.SPTL(theLoai.getMaTL()).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        if (response.body() != null) {
                            for(SanPham sanPham : response.body()){
                                //lọc sản phẩm thể loại
                                if(sanPham.getXetDuyet() == 2 && sanPham.getMaSV() != maSV){
                                    sanPhamList.add(sanPham);
                                    sanPhamAdapter.setData(sanPhamList);
                                    Toast.makeText(getContext(), "Đang load sản phẩm thể loại", Toast.LENGTH_SHORT).show();
                                }
                            }
//                            sanPhamList.addAll(response.body());
//                            sanPhamAdapter.setData(sanPhamList);
//                            Toast.makeText(getContext(), "Đang load sản phẩm thể loại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Không load được dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
                SuKien.dismissDialog();
            }
        });

        sanPhamAdapter.setOnClickListener(new SanPhamAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                SuKien.showDialog(getContext());
                SanPham sanPham = sanPhamList.get(pos);
                MASP = sanPham.getMaSP();
                startActivity(new Intent(getActivity(), ChiTietSP_Activity.class));
                SuKien.dismissDialog();
            }
        });
    }

    private void loadData() {

        //Lấy dữ liệu sản phẩm
        APIService.apiService.getSanPhamxetduyet(maSV, 2).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.body() != null) {
                    sanPhamList.addAll(response.body());
                    sanPhamAdapter.setData(sanPhamList);
                } else {
                    Log.e("loadData", "Response error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.e("loadSANPHAM", "Error: " + t.getMessage());
            }
        });

        //Lấy dữ liệu thể loại sản phẩm
        APIService.apiService.getTheLoai().enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                if (response.body() != null) {
                    listTL.addAll(response.body());
                    theLoaiSPAdapter.setData(listTL);
                }
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                Log.e("THELOAI", "Error: " + t.getMessage());
            }
        });
        SuKien.dismissDialog();
    }

    private void setUpView() {
        listTL = new ArrayList<>();
        theLoaiSPAdapter = new TheLoaiSPAdapter(getActivity(), listTL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tlSanPhamRecycler.setLayoutManager(linearLayoutManager);
        tlSanPhamRecycler.setAdapter(theLoaiSPAdapter);

        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getActivity(), sanPhamList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        spItemRecycler.setLayoutManager(linearLayoutManager2);
        spItemRecycler.setAdapter(sanPhamAdapter);
    }

    private void anhxa(View view) {
        SuKien.showDialog(getContext());
        tlSanPhamRecycler = view.findViewById(R.id.tl_recycler);
        spItemRecycler = view.findViewById(R.id.sanpham_recycler);
        txt_tatca = view.findViewById(R.id.txt_tatca);
        txt_tk = view.findViewById(R.id.txt_tk);
        imageSlider = view.findViewById(R.id.image_sliderMain);
    }
}
