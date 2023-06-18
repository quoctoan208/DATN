package com.example.datn.Adapter;

import static com.example.datn.BUS.SuKien.formatter;
import static com.example.datn.Fragment.CXNFragment.getdata_CXN;
import static com.example.datn.Fragment.DonbanCXNFragment.getdata_CXNDONBAN;
import static com.example.datn.Fragment.DonbanCXNFragment.setUpViewCNXBAN;
import static com.example.datn.Fragment.DonbanDHFragment.getdataDonHuyBAN;
import static com.example.datn.Fragment.DonbanDHFragment.setUpViewDonHuyBAN;
import static com.example.datn.Fragment.DonbanDaGFragment.getdataDaGBan;
import static com.example.datn.Fragment.DonbanDaGFragment.setUpViewDaGBan;
import static com.example.datn.Fragment.Donban_ChogiaohangFragment.getdataCHOGIAOHANG;
import static com.example.datn.Fragment.Donban_ChogiaohangFragment.setUpViewChoGiao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
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
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.ChiTietDonHang;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangDaBanAdapter extends RecyclerView.Adapter<DonHangDaBanAdapter.donhang_ViewHolder> {
    List<DonHang> donHangs;
    Activity activity;

    private DonHangDaBanAdapter.onItemClickListener listener;


    public interface onItemClickListener {
        void onItemClick(int pos, View view);
    }

    public void setOnClickListener(DonHangDaBanAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<DonHang> list) {
        this.donHangs = list;
        notifyDataSetChanged();
    }


    public DonHangDaBanAdapter(List<DonHang> donHangs, Activity activity) {
        this.donHangs = donHangs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public donhang_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhangdaban_row_item, parent, false);
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
        holder.txt_tt.setText("Tổng tiền: " + formatter.format(donHang.getTongTienThanhToan()) + " VND");
        holder.txt_thoigian.setText("Ngày giao dịch: "+donHang.getNgayGiaoDich());
        switch (donHang.getTrangThaiDH()) {
            case 0://đơn hàng đang chờ xác nhận hoặc hủy
                holder.buttonhuydonban.setText("Hủy đơn hàng");
                holder.txt_tinhtrangdonban.setText("Trạng thái: Đơn hàng chờ xác nhận.");
                huydon(donHang, holder);
                break;
            case 1: // đơn hàng đang được soạn để gửi đi
                holder.btn_xacnhandonban.setText("Xác nhận gửi hàng");
                holder.txt_tinhtrangdonban.setText("Trạng thái: Đơn hàng đang chờ gửi cho nhà vận chuyển. Hãy xác nhận khi ĐƠN VỊ VẬN CHUYỂN đã nhận đơn hàng.");
                holder.buttonhuydonban.setVisibility(View.GONE);
                xacnhanguihang(donHang,holder);
                break;
            case 2: // đơn hàng đã được gửi
                holder.buttonhuydonban.setVisibility(View.GONE);
                holder.txt_tinhtrangdonban.setText("Trạng thái: Đơn hàng đang được giao. Chờ phản hồi nhận hàng từ người mua !");
                holder.btn_xacnhandonban.setVisibility(View.GONE);
                break;
            case 3: // khách đã nhận hàng
                holder.buttonhuydonban.setVisibility(View.GONE);
                holder.btn_xacnhandonban.setVisibility(View.GONE);
                holder.txt_tinhtrangdonban.setText("Trạng thái: Khách hàng đã nhận hàng.");
                //mualai(holder, donHang);
                break;
            case 4: // đơn hàng đã bị hủy
                holder.buttonhuydonban.setVisibility(View.GONE);
                holder.btn_xacnhandonban.setVisibility(View.GONE);
                holder.txt_tinhtrangdonban.setText("Trạng thái: Đơn hàng đã bị hủy.");
                //mualai(holder, donHang);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void huydon(DonHang donHang, donhang_ViewHolder holder) {
        DonHang donHang1 = new DonHang(donHang.getDiaChiGiaoHang(), donHang.getPhuongThucThanhToan(),donHang.getTongTienThanhToan()
                ,4,donHang.getMaSVMua(),donHang.getMaSVBan(),donHang.getMaDH(), donHang.getNgayGiaoDich(), donHang.getSDT(),
                donHang.getHoVaTen(), donHang.getGhiChu());
        holder.buttonhuydonban.setOnClickListener(new View.OnClickListener() {
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
                                SuKien.showDialog(activity);
                                APIService.apiService.PutDONHANG(donHang.getMaDH(), donHang1).enqueue(new Callback<List<DonHang>>() {
                                    @Override
                                    public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                                        setUpViewCNXBAN();
                                        getdata_CXNDONBAN();
                                        SuKien.dismissDialog();
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


        DonHang donHang2 = new DonHang(donHang.getDiaChiGiaoHang(), donHang.getPhuongThucThanhToan(),donHang.getTongTienThanhToan()
                ,1,donHang.getMaSVMua(),donHang.getMaSVBan(),donHang.getMaDH(), donHang.getNgayGiaoDich(), donHang.getSDT(),
                donHang.getHoVaTen(), donHang.getGhiChu());
        holder.btn_xacnhandonban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("XÁC NHẬN ĐƠN HÀNG")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Xác Nhận", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SuKien.showDialog(activity);
                                APIService.apiService.PutDONHANG(donHang.getMaDH(), donHang2).enqueue(new Callback<List<DonHang>>() {
                                    @Override
                                    public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                                        setUpViewCNXBAN();
                                        getdata_CXNDONBAN();
                                        SuKien.dismissDialog();
                                    }

                                    @Override
                                    public void onFailure(Call<List<DonHang>> call, Throwable t) {
                                        Toast.makeText(activity, "xác nhận đơn "+ donHang.getMaDH()+ " không thành công", Toast.LENGTH_SHORT).show();
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
    public void xacnhanguihang(DonHang donHang, donhang_ViewHolder holder) {
        DonHang donHang1 = new DonHang(donHang.getDiaChiGiaoHang(), donHang.getPhuongThucThanhToan(),donHang.getTongTienThanhToan()
                ,2,donHang.getMaSVMua(),donHang.getMaSVBan(),donHang.getMaDH(), donHang.getNgayGiaoDich(), donHang.getSDT(),
                donHang.getHoVaTen(), donHang.getGhiChu());

        holder.btn_xacnhandonban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Xác nhận đã gửi hàng cho nhà vận chuyển")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SuKien.showDialog(activity);
                                APIService.apiService.PutDONHANG(donHang.getMaDH(), donHang1).enqueue(new Callback<List<DonHang>>() {
                                    @Override
                                    public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                                        setUpViewChoGiao();
                                        getdataCHOGIAOHANG();
                                        SuKien.dismissDialog();
                                    }

                                    @Override
                                    public void onFailure(Call<List<DonHang>> call, Throwable t) {
                                        Toast.makeText(activity, "Không thể xác nhận được !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });

                builder.create();
                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return donHangs.size();
    }

    public class donhang_ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_madh,txtSDT, txt_diachi, txt_hinhthucthanhtoan, txt_tinhtrangdonban, txt_tt, txt_thoigian;
        Button buttonhuydonban, btn_xacnhandonban;

        public donhang_ViewHolder(@NonNull View itemView, DonHangDaBanAdapter.onItemClickListener listener) {
            super(itemView);
            txtSDT = itemView.findViewById(R.id.txt_SDT_donhangban);
            txt_madh = itemView.findViewById(R.id.txt_madonhang_donhangban);
            txt_diachi = itemView.findViewById(R.id.txt_diachinhan_donhangbanban);
            txt_hinhthucthanhtoan = itemView.findViewById(R.id.txt_phuongthucthanhtoan_donhangban);
            txt_tinhtrangdonban = itemView.findViewById(R.id.txt_donban_tinhtrangdonban);
            txt_tt = itemView.findViewById(R.id.txt_tongtien_donhangban);
            txt_thoigian = itemView.findViewById(R.id.txt_thoigian_donhangban);
            buttonhuydonban = itemView.findViewById(R.id.btn_huydonhangban);
            btn_xacnhandonban = itemView.findViewById(R.id.btn_xacnhandonhangban);
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
