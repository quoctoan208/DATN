package com.example.datn.Fragment;

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

import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.GUI.ChiTietSP_Activity;
import com.example.datn.GUI.SuaSanPham;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SanPhamDaDangFragment extends Fragment {

    List<SanPham> sanPhamList1, sanPhamList2;
    SanPhamAdapter sanPhamAdapter1, sanPhamAdapter2;
    SanPham sanPham;
    RecyclerView recyclerView_1,recyclerView_2;
    LinearLayout linearLayout1,LinearLayout2;
    public static String maSPEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sanphamdadang, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        setUpView();
        getData();
        onclicks();
    }

    private void onclicks() {
        sanPhamAdapter1.setOnClickListener(new SanPhamAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                SanPham sanPham = sanPhamList1.get(pos);
                maSPEdit = sanPham.getMaSP();
                startActivity(new Intent(getActivity(), SuaSanPham.class));
            }
        });
    }

    private void getData() {
        //Lấy dữ liệu sản phẩm của tôi = msv
        APIService.apiService.getSanPhamcuatoi(maSV).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if(response.body() != null){
                    for(SanPham sp : response.body()){
                        if (sp.getXetDuyet() == 1){
                            sanPhamList1.add(sp);
                            linearLayout1.setVisibility(View.GONE);
                        }else if (sp.getXetDuyet() == 2){
                            sanPhamList2.add(sp);
                            LinearLayout2.setVisibility(View.GONE);
                        }
                    }
                    sanPhamAdapter1.setData(sanPhamList1);
                    sanPhamAdapter2.setData(sanPhamList2);
                }
                else {
                    Toast.makeText(getContext(), "Bạn chưa đăng sản phẩm nào!", Toast.LENGTH_SHORT).show();
                    linearLayout1.setVisibility(View.VISIBLE);
                    LinearLayout2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi không thể get API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpView() {
        sanPhamList1 = new ArrayList<>();
        sanPhamList2 = new ArrayList<>();
        sanPhamAdapter1 = new SanPhamAdapter(getContext(),sanPhamList1);
        sanPhamAdapter2 = new SanPhamAdapter(getContext(),sanPhamList2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_1.setLayoutManager(linearLayoutManager);
        recyclerView_2.setLayoutManager(linearLayoutManager2);
        recyclerView_1.setAdapter(sanPhamAdapter1);
        recyclerView_2.setAdapter(sanPhamAdapter2);
    }

    private void anhxa(View view) {
        linearLayout1 = view.findViewById(R.id.ln_khongsanpham);
        LinearLayout2 = view.findViewById(R.id.ln_khongsanpham2);
        recyclerView_1 = view.findViewById(R.id.sp_recycler_chuaxetduyt);
        recyclerView_2= view.findViewById(R.id.sp_recycler_daxetduyet);
    }
}