package com.example.datn.Fragment;

import static com.example.datn.Fragment.CXNFragment.donHangActivity;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

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
import com.example.datn.Api.APIService;
import com.example.datn.Model.DonHang;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaGFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    public DaGFragment() {
        // Required empty public constructor
    }



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
        getdata();
    }
    private void getdata() {
        APIService.apiService.Getalldonhangmua(maSV,2).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    DonHangAdapter order_adapterBUS = new DonHangAdapter(response.body(), getActivity());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(order_adapterBUS);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                Toast.makeText(donHangActivity.getApplication(), "Không tìm thấy đơn CXN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa(View view) {
        recyclerView=view.findViewById(R.id.RecyclerView);
        linearLayout=view.findViewById(R.id.LinearLayout);
    }
}