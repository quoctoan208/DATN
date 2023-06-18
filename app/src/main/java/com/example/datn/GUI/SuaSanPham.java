package com.example.datn.GUI;

import static com.example.datn.Fragment.HomeFragment.listTL;
import static com.example.datn.Fragment.SanPhamDaDangFragment.maSPEdit;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.datn.Adapter.SpinnerAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.BUS.SuKien;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.AnhSP;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaSanPham extends AppCompatActivity {

    SpinnerAdapter spinnerAdapter;
    Spinner spinner;
    EditText edt_suatensp, edt_suasoluong, edt_suadongia, edt_suamotasp;
    Button btn_suaSanPham, btn_chonsuaanh, btn_xoaSP,btn_cancel;
    List<ImageView> imageViews = new ArrayList<>();
    List<ImageView> imageViews1 = new ArrayList<>();
    List<Uri> imgUri = new ArrayList<>();
    ImageView img1, img2, img3, img4, img5;
    public static List<AnhSP> anhSPList;
    private static final int REQUEST_SELECT_IMAGES = 1;
    private SanPham sanPham;
    AnhSP anhSP;
    String mAnh1;
    String maTL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suasanpham);
        anhxa();
        setUp();
        getData();
        onClicks();
    }

    private void onClicks() {
        btn_suaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpDatesanphamAPI();
            }
        });
        btn_xoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIService.apiService.DeleteSPbyID(maSPEdit).enqueue(new Callback<SanPham>() {
                    @Override
                    public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                        onBackPressed();
                        finish();
                        Toast.makeText(SuaSanPham.this, "Đã xóa sản phẩm.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<SanPham> call, Throwable t) {
                        Toast.makeText(SuaSanPham.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getData() {
        SuKien.showDialog(SuaSanPham.this);
        APIService.apiService.GetSANPHAM(maSPEdit).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.body() != null) {
                    edt_suatensp.setText(response.body().getTenSP());
                    edt_suasoluong.setText(response.body().getSoLuong() + "");
                    edt_suadongia.setText(response.body().getDonGia() + "");
                    edt_suamotasp.setText(response.body().getMatoSP());
                    mAnh1 = response.body().getAnhSP();
                    SuKien.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                SuKien.dismissDialog();
            }
        });

        APIService.apiService.GetAnhSP(maSPEdit).enqueue(new Callback<AnhSP>() {
            @Override
            public void onResponse(Call<AnhSP> call, Response<AnhSP> response) {
                if (response.body() != null) {
                    AnhSP anhSP1 = response.body();
                    List<String> anhSPs = Arrays.asList(anhSP1.getAnh1(), anhSP1.getAnh2(), anhSP1.getAnh3(), anhSP1.getAnh4(), anhSP1.getAnh5());
                    imageViews1 = Arrays.asList(img1, img2, img3, img4, img5);
                    for (int i = 0; i < anhSPs.size(); i++) {
                        Glide.with(SuaSanPham.this)
                                .load(anhSPs.get(i))
                                .apply(new RequestOptions()
                                        .transform(new CenterCrop())
                                        .transform(new RoundedCorners(15)))
                                .error(R.drawable.add_product)
                                .into(imageViews1.get(i));
                    }

                }
            }

            @Override
            public void onFailure(Call<AnhSP> call, Throwable t) {
                Toast.makeText(SuaSanPham.this, "Không thể load ảnh, hãy thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUp() {
        sanPham = new SanPham();
        anhSPList = new ArrayList<>();
        anhSP = new AnhSP();
        spinnerAdapter = new SpinnerAdapter(this, R.layout.item_select_spiner, getlistTheLoai());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTL = spinnerAdapter.getItem(i).getMaTL();
                Toast.makeText(SuaSanPham.this, " Chọn " + maTL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<TheLoai> getlistTheLoai() {
        List<TheLoai> theLoais = new ArrayList<>();
        theLoais = listTL;
        return theLoais;
    }

    public void UpDatesanphamAPI(){
        SuKien.showDialog(SuaSanPham.this);
        String mTenSP = edt_suatensp.getText().toString();
        int mSoLuong = Integer.parseInt(edt_suasoluong.getText().toString());
        float mDonGia = Float.parseFloat(edt_suadongia.getText().toString());
        String mMota = edt_suamotasp.getText().toString();

        sanPham = new SanPham(maSPEdit, maTL, mTenSP, mAnh1, mDonGia, mSoLuong, mMota, 1, maSV);

        APIService.apiService.putSANPHAM(maSPEdit,sanPham).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()){
                    onBackPressed();
                    Toast.makeText(SuaSanPham.this, "Update san pham thanh cong", Toast.LENGTH_SHORT).show();
                    SuKien.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                SuKien.dismissDialog();
            }
        });

    }

    private void anhxa() {
        spinner = findViewById(R.id.planets_spinner_suasp);
        edt_suatensp = findViewById(R.id.edt_sua_tensp);
        edt_suasoluong = findViewById(R.id.edt_sua_soluongsp);
        edt_suadongia = findViewById(R.id.edt_sua_giabansp);
        edt_suamotasp = findViewById(R.id.edt_sua_motasp);
        btn_chonsuaanh = findViewById(R.id.btn_suaanhsp);
        btn_suaSanPham = findViewById(R.id.btn_submitsuasanpham);
        btn_xoaSP = findViewById(R.id.btn_xoasanpham);
        btn_cancel = findViewById(R.id.btn_cancesuasanpham);

        img1 = findViewById(R.id.suasp_imgAnhSP1);
        img2 = findViewById(R.id.suasp_imgAnhSP2);
        img3 = findViewById(R.id.suasp_imgAnhSP3);
        img4 = findViewById(R.id.suasp_imgAnhSP4);
        img5 = findViewById(R.id.suasp_imgAnhSP5);

        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.suasp_imgAnhSP1));
        imageViews.add(findViewById(R.id.suasp_imgAnhSP2));
        imageViews.add(findViewById(R.id.suasp_imgAnhSP3));
        imageViews.add(findViewById(R.id.suasp_imgAnhSP4));
        imageViews.add(findViewById(R.id.suasp_imgAnhSP5));
    }
}

