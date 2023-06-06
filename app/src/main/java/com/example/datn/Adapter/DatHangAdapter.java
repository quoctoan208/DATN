package com.example.datn.Adapter;

import static com.example.datn.BUS.SuKien.formatter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.R;
import com.example.datn.Api.APIService;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangAdapter extends RecyclerView.Adapter<DatHangAdapter.DatHangviewhodler> {
    List<GioHang> list;
    Context context;

    public DatHangAdapter(List<GioHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DatHangviewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dathang_row_item, parent, false);
        return new DatHangviewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatHangviewhodler holder, @SuppressLint("RecyclerView") int position) {
        GioHang GioHang = list.get(position);
        holder.txt_sl.setText("Số lượng: "+GioHang.getSoLuong() + "");
        get_data(GioHang, holder);
    }

    public void get_data(GioHang GioHang, DatHangviewhodler holder) {
        APIService.apiService.GetSANPHAM(GioHang.getMaSP()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {
                    SanPham sanPham = response.body();
                    Glide.with(context).load(sanPham.getAnhSP()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.anhspdemo).into(holder.img);
                    holder.txt_name.setText(sanPham.getTenSP());
                    holder.txt_nguoiban.setText( "MaSV: "+sanPham.getMaSV());
                    holder.txt_gia.setText(formatter.format(sanPham.getDonGia()) + " VNĐ");
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(context, "Không lấy được thông tin giỏn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class DatHangviewhodler extends RecyclerView.ViewHolder {
        ImageView img  ;
        TextView txt_name, txt_nguoiban, txt_gia, txt_sl;

        public DatHangviewhodler(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_anhsp_dathang);
            txt_name = itemView.findViewById(R.id.txt_tensp_dathang);
            txt_nguoiban = itemView.findViewById(R.id.txt_masvban_dathang);
            txt_gia = itemView.findViewById(R.id.txt_dongiaSP_dathang);
            txt_sl = itemView.findViewById(R.id.txt_soluongsp_dathang);
        }
    }
}
