package com.example.datn.Adapter;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.HomeFragment.MASP;
import static com.example.datn.GUI.CXNFragment.getdata_CXN;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.Api.APIService;
import com.example.datn.GUI.ChiTietSP_Activity;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.donhang_ViewHolder> {
    List<DonHang> donHangs;
    Activity activity;

    public DonHangAdapter(List<DonHang> donHangs, Activity activity) {
        this.donHangs = donHangs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public donhang_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang_row_item, parent, false);
        return new donhang_ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull donhang_ViewHolder holder, int position) {
        DonHang donHang = donHangs.get(position);
        get_data(donHang, holder);

        holder.txt_sl.setText("Số lượng: " + donHang.getSoLuong());
        holder.txt_tt.setText("Tổng tiền: " + formatter.format(donHang.getTongTien()) + " VND");
        holder.txt_thoigian.setText(donHang.getThoigian());
        switch (donHang.getTrangthai()) {
            case 0:
                holder.button.setText("Hủy đơn hàng");
                huydon(donHang, holder);
                break;
            case 1:
                holder.button.setVisibility(View.GONE);
                break;
            case 2:
                holder.button.setText("Mua lại sản phẩm");
                mualai(holder, donHang);
                break;
            case 3:
                holder.button.setText("Mua lại sản phẩm");
                mualai(holder, donHang);
                break;
        }
    }

    public void get_data(DonHang donHang, donhang_ViewHolder holder) {
        APIService.apiService.GetSANPHAM(donHang.getMaSP()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {
                    SanPham sanPham = response.body();
                    Glide.with(activity).load(sanPham.getAnhSP()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.anhspdemo).into(holder.img);
                    holder.txt_name.setText(sanPham.getTenSP());
                    holder.txt_mota.setText(sanPham.getMatoSP());
                    holder.txt_gia.setText(formatter.format(sanPham.getDonGia()) + " VNĐ");
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void huydon(DonHang donHang, donhang_ViewHolder holder) {
        DonHang donHang1 = new DonHang(donHang.getMaDH(), donHang.getSoLuong(), donHang.getTongTien(), 3, donHang.getUseName(), donHang.getMaSP(), donHang.getDiachi(),
                donHang.getSdt(), donHang.getTinnhan(), java.time.LocalDateTime.now() + "");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Bạn có muốn hủy đơn này không")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                APIService.apiService.PutDONHANG(donHang.getMaDH(), donHang1).enqueue(new Callback<List<DonHang>>() {
                                    @Override
                                    public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                                        getdata_CXN();
                                    }

                                    @Override
                                    public void onFailure(Call<List<DonHang>> call, Throwable t) {

                                    }
                                });
                            }

                        });

                builder.create();
                builder.show();
            }
        });
    }

    public void mualai(donhang_ViewHolder holder, DonHang donHang) {
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MASP = donHang.getMaSP();
                activity.startActivity(new Intent(activity, ChiTietSP_Activity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (donHangs != null) {
            return donHangs.size();
        }
        return 0;
    }

    public class donhang_ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_name, txt_mota, txt_gia, txt_sl, txt_tt, txt_thoigian;
        Button button;

        public donhang_ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_mota = itemView.findViewById(R.id.txt_mota);
            txt_gia = itemView.findViewById(R.id.txt_gia);
            txt_sl = itemView.findViewById(R.id.txt_sl);
            txt_tt = itemView.findViewById(R.id.txt_tt);
            txt_thoigian = itemView.findViewById(R.id.txt_thoigian);
            button = itemView.findViewById(R.id.btn_huydonhang);
        }
    }
}
