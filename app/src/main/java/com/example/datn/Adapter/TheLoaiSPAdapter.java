package com.example.datn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Api.APIService;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheLoaiSPAdapter extends RecyclerView.Adapter<TheLoaiSPAdapter.ProductViewHolder> {

    Context context;
    List<TheLoai> theLoaiList;

    private TheLoaiSPAdapter.onItemClickListener listener;


    public interface onItemClickListener {
        void onItemClick(int pos, View view);
    }
    public void setOnClickListener(TheLoaiSPAdapter.onItemClickListener listener) {
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TheLoai> list) {
        this.theLoaiList = list;
        notifyDataSetChanged();
    }
    public TheLoaiSPAdapter(Context context, List<TheLoai> theLoaiList) {
        this.context = context;
        this.theLoaiList = theLoaiList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theloai_row_item, parent, false);
        // lets create a recyclerview row item layout file
        return new ProductViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TheLoai theLoai = theLoaiList.get(position);
        holder.tenTL.setText(theLoai.getTenTL());
    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tenTL;

        public ProductViewHolder(@NonNull View itemView , TheLoaiSPAdapter.onItemClickListener listener) {
            super(itemView);
            tenTL = itemView.findViewById(R.id.tentheloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, v);
                        }
                    }
                }
            });
        }
    }

}
