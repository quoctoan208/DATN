package com.example.datn.Adapter;


import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.GUI.GioHangActivity.getdata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.Api.APIService;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.gio_hang_viewhodler> {
    List<GioHang> list;
    Activity context;

    public GioHangAdapter(List<GioHang> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public gio_hang_viewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giohang_row_item, parent, false);
        return new gio_hang_viewhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull gio_hang_viewhodler holder, @SuppressLint("RecyclerView") int position) {
        GioHang GioHang = list.get(position);
        holder.txt_sl.setText(GioHang.getSoLuong()+"");
        final int[] sosp = {GioHang.getSoLuong()};
        final int[] sosp1 = {GioHang.getSoLuong()};
        get_data(GioHang, holder);
        holder.img_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sosp1[0] = sosp[0] - 1;
                if (sosp[0] != 1) {
                    sosp[0] = sosp[0] - 1;
                    holder.txt_sl.setText(sosp[0] + "");
                    GioHang gioHang1 = new GioHang(GioHang.getIDGIOHANG(), GioHang.getMaSV(), GioHang.getMaSP(), sosp[0], sosp[0] * Integer.parseInt(holder.txt_tt.getText().toString()));
                    Putgiohang(GioHang.getIDGIOHANG(), gioHang1);
                }
                if (sosp1[0] <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn xóa sản phẩm này ko")
                            .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    APIService.apiService.DeleteGIOHANG(GioHang.getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                        @Override
                                        public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(context, "Xóa giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                getdata();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GioHang> call, Throwable t) {

                                        }
                                    });
                                }
                            });


                    builder.create();
                    builder.show();
                }
            }
        });
        holder.img_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sosp[0] = sosp[0] + 1;
                sosp1[0] = sosp[0];
                holder.txt_sl.setText(sosp[0] + "");
                GioHang gioHang1 = new GioHang(GioHang.getIDGIOHANG(), GioHang.getMaSV(), GioHang.getMaSP(), sosp[0], sosp[0] * Integer.parseInt(holder.txt_tt.getText().toString()));
                Putgiohang(GioHang.getIDGIOHANG(), gioHang1);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa sản phẩm này ko")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                APIService.apiService.DeleteGIOHANG(GioHang.getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                    @Override
                                    public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Xóa giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                            getdata();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GioHang> call, Throwable t) {

                                    }
                                });
                            }
                        });

                builder.create();
                builder.show();
            }
        });
    }

    public void get_data(GioHang GioHang, gio_hang_viewhodler holder) {
        APIService.apiService.GetSANPHAM(GioHang.getMaSP()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {
                    SanPham sanPham = response.body();
                    Glide.with(context).load(sanPham.getAnhSP()).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(15))).error(R.drawable.anhspdemo).into(holder.img);
                    holder.txt_name.setText(sanPham.getTenSP());
                    holder.txt_mota.setText(sanPham.getMatoSP());
                    holder.txt_gia.setText(formatter.format(sanPham.getDonGia()) + " VNĐ");
                    holder.txt_tt.setText("" + sanPham.getDonGia());
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });
    }

    public void Putgiohang(int id, GioHang gioHang) {

        APIService.apiService.PutGIOHANG(id, gioHang).enqueue(new Callback<GioHang>() {
            @Override
            public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                getdata();
            }

            @Override
            public void onFailure(Call<GioHang> call, Throwable t) {

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

    public class gio_hang_viewhodler extends RecyclerView.ViewHolder {
        ImageView img, img_tru, img_cong, img_delete;
        TextView txt_name, txt_mota, txt_gia, txt_sl, txt_tt;

        public gio_hang_viewhodler(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            img_tru = itemView.findViewById(R.id.img_tru);
            img_cong = itemView.findViewById(R.id.img_cong);
            img_delete = itemView.findViewById(R.id.img_delete);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_mota = itemView.findViewById(R.id.txt_mota);
            txt_gia = itemView.findViewById(R.id.txt_gia);
            txt_sl = itemView.findViewById(R.id.txt_sl);
            txt_tt = itemView.findViewById(R.id.txt_tt);
        }
    }
}
