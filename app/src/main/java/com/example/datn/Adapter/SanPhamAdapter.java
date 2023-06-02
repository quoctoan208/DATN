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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.GUI.ChiTietSP_Activity;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ProductViewHolder> {

    Context context;
    List<SanPham> sanPhamList;


    public SanPhamAdapter(Context context, List<SanPham> productsList) {
        this.context = context;
        this.sanPhamList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham=sanPhamList.get(position);
                Glide.with(context).load(sanPham.getAnhSP()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.anhspdemo).into(holder.img);
                holder.txt_name.setText(sanPham.getTenSP());
                holder.txt_gia.setText(formatter.format(sanPham.getDonGia())+ " VNĐ");
                holder.txt_sl.setText("Số lượng: "+sanPham.getSoLuong());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MASP = sanPhamList.get(position).getMaSP();
                    context.startActivity(new Intent(context, ChiTietSP_Activity.class));
                }
            });
        }




    @Override
    public int getItemCount() {
        if (sanPhamList!=null){
            return sanPhamList.size();
        }
        return 0;
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView img;
        TextView txt_name, txt_sl, txt_gia;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            img = itemView.findViewById(R.id.img);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_sl = itemView.findViewById(R.id.txt_sl);
            txt_gia = itemView.findViewById(R.id.txt_gia);


        }
    }

}
