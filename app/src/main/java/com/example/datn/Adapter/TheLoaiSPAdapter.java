package com.example.datn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Api.APIService;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheLoaiSPAdapter extends RecyclerView.Adapter<TheLoaiSPAdapter.ProductViewHolder> {

    Context context;

    List<TheLoai> theLoaiList;
    RecyclerView recyclerView;

    public TheLoaiSPAdapter(Context context, List<TheLoai> theLoaiList, RecyclerView recyclerView) {
        this.context = context;
        this.theLoaiList = theLoaiList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.theloai_row_item, parent, false);
        // lets create a recyclerview row item layout file
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tenTL.setText(theLoaiList.get(position).getTenTL());
        holder.tenTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService.apiService.SPTL(theLoaiList.get(position).getMaTL()).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(context, response.body());
                        recyclerView.setAdapter(sanPhamAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }


    public static final class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView tenTL;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tenTL = itemView.findViewById(R.id.tentheloai);

        }
    }

}
