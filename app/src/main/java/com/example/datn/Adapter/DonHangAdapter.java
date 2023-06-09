package com.example.datn.Adapter;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.CXNFragment.getdata_CXN;
import static com.example.datn.Fragment.CXNFragment.setUpViewCXN;
import static com.example.datn.Fragment.DHFragment.getdataDaH;
import static com.example.datn.Fragment.DHFragment.setUpViewDaH;
import static com.example.datn.Fragment.DaGFragment.getdataDaG;
import static com.example.datn.Fragment.DaGFragment.setUpViewDaG;
import static com.example.datn.Fragment.DangGFragment.getdataDangG;
import static com.example.datn.Fragment.DangGFragment.setUpViewDangG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Api.APIService;
import com.example.datn.Model.DonHang;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.donhang_ViewHolder> {
    List<DonHang> donHangs;
    Activity activity;

    private DonHangAdapter.onItemClickListener listener;


    public interface onItemClickListener {
        void onItemClick(int pos, View view);
    }

    public void setOnClickListener(DonHangAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<DonHang> list) {
        this.donHangs = list;
        notifyDataSetChanged();
    }


    public DonHangAdapter(List<DonHang> donHangs, Activity activity) {
        this.donHangs = donHangs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public donhang_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang_row_item, parent, false);
        return new donhang_ViewHolder(view, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull donhang_ViewHolder holder, int position) {
        DonHang donHang = donHangs.get(position);
        holder.txt_madh.setText("MÃ ĐH: " + donHang.getMaDH());
        holder.txtSDT.setText("SĐT: 0"+donHang.getSDT());
        holder.txt_diachi.setText("ĐC: "+donHang.getDiaChiGiaoHang());
        holder.txt_hinhthucthanhtoan.setText(donHang.getPhuongThucThanhToan());
        holder.txt_msvban.setText("Người bán: "+donHang.getMaSVBan());
        holder.txt_tt.setText("Tổng tiền: " + formatter.format(donHang.getTongTienThanhToan()) + " VND");
        holder.txt_thoigian.setText("Ngày giao dịch: "+donHang.getNgayGiaoDich());
        switch (donHang.getTrangThaiDH()) {
            case 0:
                holder.txt_trangthaidonmua.setText("Đơn hàng đang chờ xác nhận.");
                holder.button.setText("Hủy đơn hàng");
                huydon(donHang, holder);
                break;
            case 1:
                holder.txt_trangthaidonmua.setText("Người bán đang chuẩn bị đơn hàng.");
                holder.button.setVisibility(View.GONE);
                break;
            case 2:
                holder.txt_trangthaidonmua.setText("Đơn vị vận chuyển đã nhận hàng từ người bán.");
                holder.button.setText("Đã nhận hàng");
                danhanhang(donHang, holder);
                break;
            case 3:
                holder.txt_trangthaidonmua.setText("Đơn hàng đã được giao thành công.");
                holder.button.setText("Mua lại sản phẩm");
                //mualai(holder, donHang);
                break;
            case 4:
                holder.txt_trangthaidonmua.setText("Đơn hàng đã bị hủy.");
                holder.button.setText("Mua lại sản phẩm");
                //mualai(holder, donHang);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void huydon(DonHang donHang, donhang_ViewHolder holder) {
        DonHang donHang1 = new DonHang(donHang.getDiaChiGiaoHang(), donHang.getPhuongThucThanhToan(),donHang.getTongTienThanhToan()
                ,4,donHang.getMaSVMua(),donHang.getMaSVBan(),donHang.getMaDH(), donHang.getNgayGiaoDich(), donHang.getSDT(),
                donHang.getHoVaTen(), donHang.getGhiChu());
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
                                        setUpViewCXN();
                                        getdata_CXN();
                                        setUpViewDaH();
                                        getdataDaH();
                                    }

                                    @Override
                                    public void onFailure(Call<List<DonHang>> call, Throwable t) {
                                        Toast.makeText(activity, "hủy đơn không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });

                builder.create();
                builder.show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void danhanhang(DonHang donHang, donhang_ViewHolder holder) {
        DonHang donHang1 = new DonHang(donHang.getDiaChiGiaoHang(), donHang.getPhuongThucThanhToan(),donHang.getTongTienThanhToan()
                ,3,donHang.getMaSVMua(),donHang.getMaSVBan(),donHang.getMaDH(), donHang.getNgayGiaoDich(), donHang.getSDT(),
                donHang.getHoVaTen(), donHang.getGhiChu());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Đã nhận hàng")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                APIService.apiService.PutDONHANG(donHang.getMaDH(), donHang1).enqueue(new Callback<List<DonHang>>() {
                                    @Override
                                    public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                                        setUpViewDangG();
                                        getdataDangG();
                                        setUpViewDaG();
                                        getdataDaG();
                                    }

                                    @Override
                                    public void onFailure(Call<List<DonHang>> call, Throwable t) {
                                        Toast.makeText(activity, "hủy đơn không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });

                builder.create();
                builder.show();
            }
        });
    }

//    public void mualai(donhang_ViewHolder holder, DonHang donHang) {
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MASP = donHang.getMaSP();
//                activity.startActivity(new Intent(activity, ChiTietSP_Activity.class));
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return donHangs.size();
    }

    public class donhang_ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_madh,txtSDT, txt_diachi, txt_hinhthucthanhtoan, txt_msvban, txt_tt, txt_thoigian,txt_trangthaidonmua;
        Button button;

        public donhang_ViewHolder(@NonNull View itemView, DonHangAdapter.onItemClickListener listener) {
            super(itemView);
            txtSDT = itemView.findViewById(R.id.txt_SDT_donhang);
            txt_madh = itemView.findViewById(R.id.txt_madonhang_donhang);
            txt_diachi = itemView.findViewById(R.id.txt_diachinhan_donhang);
            txt_hinhthucthanhtoan = itemView.findViewById(R.id.txt_phuongthucthanhtoan_donhang);
            txt_msvban = itemView.findViewById(R.id.txt_nguoiban_donhang);
            txt_tt = itemView.findViewById(R.id.txt_tongtien_donhang);
            txt_thoigian = itemView.findViewById(R.id.txt_thoigian_donhang);
            txt_trangthaidonmua = itemView.findViewById(R.id.txt_trangthaidonmua);
            button = itemView.findViewById(R.id.btn_huydonhang);
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
