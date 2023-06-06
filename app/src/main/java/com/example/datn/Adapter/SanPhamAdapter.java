package com.example.datn.Adapter;


import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.HomeFragment.MASP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.GUI.ChiTietSP_Activity;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ProductViewHolder> {

    Context context;
    List<SanPham> sanPhamList;

    private SanPhamAdapter.onItemClickListener listener;


    public interface onItemClickListener {
        void onItemClick(int pos, View view);
    }
    public void setOnClickListener(SanPhamAdapter.onItemClickListener listener) {
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SanPham> list) {
        this.sanPhamList = list;
        notifyDataSetChanged();
    }

    public SanPhamAdapter(Context context, List<SanPham> productsList) {
        this.context = context;
        this.sanPhamList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_row_item, parent, false);
        return new ProductViewHolder(view, listener);
    }

    @Override
    public int getItemCount() {
        if (sanPhamList != null) {
            return sanPhamList.size();
        }
        return sanPhamList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(sanPham != null){
            Glide.with(context).load(sanPham.getAnhSP()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.anhspdemo).into(holder.img);
            holder.txt_name.setText(sanPham.getTenSP());
            holder.txt_gia.setText(formatter.format(sanPham.getDonGia()) + " VNĐ");
            holder.txt_sl.setText("Số lượng: " + sanPham.getSoLuong());
            holder.txt_masv.setText("MSV: "+sanPham.getMaSV());
        } else {
            Toast.makeText(context, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }



    public  class ProductViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView img;
        TextView txt_name, txt_sl, txt_gia, txt_masv;

        public ProductViewHolder(@NonNull View itemView , SanPhamAdapter.onItemClickListener listener) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout_itemsp);
            img = itemView.findViewById(R.id.img_sp);
            txt_name = itemView.findViewById(R.id.txt_tensp);
            txt_sl = itemView.findViewById(R.id.txt_soluongsp);
            txt_gia = itemView.findViewById(R.id.txt_dongiaSP);
            txt_masv = itemView.findViewById(R.id.txt_nguoiban);
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
