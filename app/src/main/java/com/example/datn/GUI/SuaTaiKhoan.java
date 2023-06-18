package com.example.datn.GUI;

import static com.example.datn.GUI.DangNhap_Activity.taikhoancuatoi;
import static com.example.datn.GUI.TaiKhoanActivity.getDataUser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Model.TaiKhoan;
import com.example.datn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaTaiKhoan extends AppCompatActivity {

    EditText edtTen, edtLop, edtSDT, edtDiaChi, edtMKCu, edtMKMoi, edtXacNhanMKMoi;
    Button btnDoiMK, btnXacNhan;
    LinearLayout layout_DoiMK;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtintaikhoan);
        anhxa();
        getData();
        onclick();
    }

    private void onclick() {
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_DoiMK.setVisibility(View.VISIBLE);
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapNhatThongTin();
            }
        });
    }

    private void CapNhatThongTin(){
        SuKien.showDialog(SuaTaiKhoan.this);
        if(layout_DoiMK.getVisibility() == View.VISIBLE){

            if ((edtMKCu.getText().toString().equals(taikhoancuatoi.getMatKhau())) == false){
                SuKien.dismissDialog();
                Toast.makeText(this, "Mật khẩu cũ chưa chính xác", Toast.LENGTH_SHORT).show();
            }
            else if (edtMKCu.getText().toString().isEmpty()||edtMKMoi.getText().toString().isEmpty()||edtXacNhanMKMoi.getText().toString().isEmpty()){
                SuKien.dismissDialog();
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else if (edtMKMoi.getText().toString().equals(edtXacNhanMKMoi.getText().toString()) == false) {
                SuKien.dismissDialog();
                Toast.makeText(this, "Vui lòng xác nhận lại mật khẩu mới", Toast.LENGTH_SHORT).show();
            }else {
                TaiKhoan taiKhoan = new TaiKhoan(Integer.parseInt(edtSDT.getText().toString()),edtMKMoi.getText().toString(),
                        edtTen.getText().toString(), edtDiaChi.getText().toString(),"Nam",Integer.parseInt(edtLop.getText().toString()),
                        taikhoancuatoi.getMaSV(),taikhoancuatoi.getDanhGia());

                APIService.apiService.putTAIKHOAN(taikhoancuatoi.getMaSV(),taiKhoan).enqueue(new Callback<TaiKhoan>() {
                    @Override
                    public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                        APIService.apiService.GetTaikhoan(taikhoancuatoi.getMaSV()).enqueue(new Callback<TaiKhoan>() {
                            @Override
                            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                                Toast.makeText(SuaTaiKhoan.this, "Cập nhật tải khoản thành công", Toast.LENGTH_SHORT).show();
                                SuKien.dismissDialog();
                                taikhoancuatoi = response.body();
                                getDataUser();
                                onBackPressed();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                SuKien.dismissDialog();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<TaiKhoan> call, Throwable t) {
                        SuKien.dismissDialog();
                    }
                });
            }

        }
        else {
            TaiKhoan taiKhoan = new TaiKhoan(Integer.parseInt(edtSDT.getText().toString()),taikhoancuatoi.getMatKhau(),
                    edtTen.getText().toString(), edtDiaChi.getText().toString(),"Nam",Integer.parseInt(edtLop.getText().toString()),
                    taikhoancuatoi.getMaSV(),taikhoancuatoi.getDanhGia());

            APIService.apiService.putTAIKHOAN(taikhoancuatoi.getMaSV(),taiKhoan).enqueue(new Callback<TaiKhoan>() {
                @Override
                public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                    APIService.apiService.GetTaikhoan(taikhoancuatoi.getMaSV()).enqueue(new Callback<TaiKhoan>() {
                        @Override
                        public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                            Toast.makeText(SuaTaiKhoan.this, "Cập nhật tải khoản thành công", Toast.LENGTH_SHORT).show();
                            SuKien.dismissDialog();
                            taikhoancuatoi = response.body();
                            getDataUser();
                            onBackPressed();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<TaiKhoan> call, Throwable t) {
                            SuKien.dismissDialog();
                        }
                    });
                }

                @Override
                public void onFailure(Call<TaiKhoan> call, Throwable t) {
                    SuKien.dismissDialog();
                }
            });
        }
    }

    private void getData() {
        edtTen.setText(taikhoancuatoi.getHoVaTen());
        edtLop.setText(taikhoancuatoi.getMaLop()+"");
        edtSDT.setText("0"+taikhoancuatoi.getsDT());
        edtDiaChi.setText(taikhoancuatoi.getDiaChi());
    }

    private void anhxa() {
        edtTen = findViewById(R.id.edt_suahoten);
        edtLop = findViewById(R.id.edt_suamalop);
        edtSDT = findViewById(R.id.edt_suasdt);
        edtDiaChi = findViewById(R.id.edt_suadiachi);
        edtMKCu = findViewById(R.id.edt_mkcu);
        edtMKMoi = findViewById(R.id.edt_mkmoi);
        edtXacNhanMKMoi = findViewById(R.id.edt_xacnhanmkmoi);
        layout_DoiMK = findViewById(R.id.layout_doimatkhau);
        btnDoiMK = findViewById(R.id.btn_doimatkhau);
        btnXacNhan = findViewById(R.id.btn_dcapnhatthongtin);
    }

}
