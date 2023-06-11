package com.example.datn.Adapter;


import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.GioHangFragment.getdata;
import static com.example.datn.Fragment.GioHangFragment.sanPhamThanhToanList;
import static com.example.datn.Fragment.GioHangFragment.tinhTienGioHang;
import static com.example.datn.Fragment.GioHangFragment.tongTienGioHang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.example.datn.BUS.SuKien;
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
    int GiaTien = 0;

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
        GioHang giohang = list.get(position);

        get_data(giohang, holder);

        //Giảm số lượng gỏ hàng
        holder.img_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giohang.getSoLuong() > 1){
                    holder.txt_sl.setText(""+(giohang.getSoLuong() - 1));
                    GioHang gioHang1 = new GioHang(giohang.getIDGIOHANG(), giohang.getMaSV(),
                            giohang.getMaSP(), giohang.getSoLuong() - 1, (giohang.getSoLuong() - 1) * GiaTien);
                    Putgiohang(giohang.getIDGIOHANG(), gioHang1);
                }
                else if (giohang.getSoLuong() == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có muốn xóa sản phẩm này ko")
                            .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SuKien.showDialog(context);
                                    APIService.apiService.DeleteGIOHANG(giohang.getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                        @Override
                                        public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                            if (response.isSuccessful()) {
                                                SuKien.dismissDialog();
                                                Toast.makeText(context, "Xóa sản phẩm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                getdata();
                                            }
                                            else SuKien.dismissDialog();
                                        }

                                        @Override
                                        public void onFailure(Call<GioHang> call, Throwable t) {
                                            SuKien.dismissDialog();
                                            Toast.makeText(context, "LỖI xóa sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    SuKien.dismissDialog();
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
        });


        final int[] soluongSP = new int[1];
        APIService.apiService.GetSANPHAM(giohang.getMaSP()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if(response.isSuccessful()){
                    soluongSP[0] =  response.body().getSoLuong();
                }
                else {
                    Toast.makeText(context, "Không lấy được số lượng sản phẩm db", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(context, "Lỗi không có sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        //Cộng sl giỏ hàng
        holder.img_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(soluongSP[0] == giohang.getSoLuong()){
                    Toast.makeText(context, "Số lượng sản phẩm giới hạn !", Toast.LENGTH_SHORT).show();
                }

                else if(soluongSP[0] > giohang.getSoLuong()){
                    holder.txt_sl.setText(""+(giohang.getSoLuong() + 1));
                    GioHang gioHang1 = new GioHang(giohang.getIDGIOHANG(), giohang.getMaSV(), giohang.getMaSP(),(giohang.getSoLuong() + 1),
                            (giohang.getSoLuong() + 1) * GiaTien);
                    Putgiohang(giohang.getIDGIOHANG(), gioHang1);
                }
//
//                } else
//                    Toast.makeText(context, "Chỉ có " + sosp[0] + " sản phẩm được đăng bán", Toast.LENGTH_SHORT).show();
            }
        });

        //Xóa số lượng giỏ hàng
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
                                SuKien.showDialog(context);
                                APIService.apiService.DeleteGIOHANG(giohang.getIDGIOHANG()).enqueue(new Callback<GioHang>() {
                                    @Override
                                    public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                        if (response.isSuccessful()) {
                                            SuKien.dismissDialog();
                                            getdata();
                                            Toast.makeText(context, "Xóa giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                        }
                                        else SuKien.dismissDialog();
                                    }

                                    @Override
                                    public void onFailure(Call<GioHang> call, Throwable t) {
                                        SuKien.dismissDialog();
                                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                SuKien.dismissDialog();
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
                    holder.txt_masv.setText("Người bán: " + sanPham.getMaSV());
                    holder.txt_gia.setText(formatter.format(sanPham.getDonGia()) + " VNĐ");
                    GiaTien = (int) sanPham.getDonGia();

                    holder.checkBox_itemSpGioHang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(holder.checkBox_itemSpGioHang.isChecked()){
                                sanPhamThanhToanList.add(sanPham);
                                tinhTienGioHang();
                            }
                            else {
                                sanPhamThanhToanList.remove(sanPham);
                                tinhTienGioHang();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(context, "Không thể load sản phẩm giỏ hàng", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
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
        CheckBox checkBox_itemSpGioHang;
        TextView txt_name, txt_masv, txt_gia, txt_sl;


        public gio_hang_viewhodler(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imganhSP_giohang);
            img_tru = itemView.findViewById(R.id.img_tru);
            img_cong = itemView.findViewById(R.id.img_cong);
            img_delete = itemView.findViewById(R.id.img_delete);
            txt_masv = itemView.findViewById(R.id.txt_masv_giohang);
            txt_name = itemView.findViewById(R.id.txt_tensp_giohang);
            txt_gia = itemView.findViewById(R.id.txt_dongiaSP_giohang);
            txt_sl = itemView.findViewById(R.id.txt_soluongsp_giohang);
            checkBox_itemSpGioHang = itemView.findViewById(R.id.check_itemspGioHang);
        }
    }
}
