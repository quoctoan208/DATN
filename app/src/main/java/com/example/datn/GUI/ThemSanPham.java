package com.example.datn.GUI;

import static com.example.datn.Fragment.HomeFragment.listTL;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.app.Activity;
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

import com.example.datn.Adapter.SpinnerAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.AnhSP;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.example.datn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPham extends Activity {
    private static final String TAG = "MyActivity";
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    private EditText edt_masp, edt_tensp, edt_soluong, edt_dongia, edt_motasp;
    private Button btn_addSanPham, themanh;
    List<ImageView> imageViews = new ArrayList<>();
    List<Uri> imgUri = new ArrayList<>();
    public static List<AnhSP> anhSPList;
    private static final int REQUEST_SELECT_IMAGES = 1;
    private SanPham sanPham;
    String mMasp,mAnh1,mAnh2,mAnh3,mAnh4,mAnh5;
    static AnhSP anhSP ;
    String maTL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);
        anhxa();
        setUp();
        onclick();
    }


    private void setUp() {
        anhSPList = new ArrayList<>();

        //Load spiner
        spinnerAdapter = new SpinnerAdapter(this, R.layout.item_select_spiner, getlistTheLoai());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTL = spinnerAdapter.getItem(i).getMaTL();
                Toast.makeText(ThemSanPham.this, " Chọn "+maTL, Toast.LENGTH_SHORT).show();
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

    private void onclick() {
        btn_addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addsanphamAPI();
            }
        });

        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT"), REQUEST_SELECT_IMAGES);
            }
        });
    }


    @Override // set ảnh đã thêm lên ImageView
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGES && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI(s) from the Intent
            ClipData clipData = data.getClipData();
            if (clipData != null && clipData.getItemCount() > 0 && clipData.getItemCount() <= 5) {
                // tải ảnh vừa thêm lên Imagaview
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    ImageView imageView = imageViews.get(i);
                    imgUri.add(imageUri);
                    imageView.setImageURI(imageUri);
                }
                // Xóa ảnh thừa
                for (int i = clipData.getItemCount(); i < imageViews.size(); i++) {
                    ImageView imageView = imageViews.get(i);
                    imageView.setVisibility(View.GONE);
                }
            } else {
                //Lấy uri từ ảnh được chọn
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    //thêm ảnh vào img
                    ImageView imageView = imageViews.get(0);
                    imgUri.add(imageUri);
                    imageView.setImageURI(imageUri);
                    // xóa ảnh thừa
//                    for (int i = 1; i < imageViews.size(); i++) {
//                        ImageView unusedImageView = imageViews.get(i);
//                        unusedImageView.setVisibility(View.GONE);
//                    }
                } else {
                    // Handle error if the user did not select an image
                    Toast.makeText(this, "Chưa có ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Add ảnh lên firebase
    private void addAnhSPFireBase( final int imageCount, final Uri imageUri) {
        // Tạo tên của ảnh dựa trên số thứ tự của ảnh
        final String imageName = "anh" + (imageCount + 1);

        // Tham chiếu đến Firebase Storage
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child(""+mMasp).child(imageName);

        // Tạo task để upload ảnh lên Firebase Storage
        UploadTask uploadTask = storageRef.putFile(imageUri);

        // Xử lý thành công khi upload ảnh thành công
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Lấy đường dẫn download của ảnh từ Firebase Storage
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Thay đổi trường tương ứng trong đối tượng AnhSP dựa trên số thứ tự của ảnh
                        switch (imageCount) {
                            case 0:
                                mAnh1 = downloadUri.toString();
                                break;
                            case 1:
                                mAnh2= downloadUri.toString();
                                break;
                            case 2:
                                mAnh3= downloadUri.toString();
                                break;
                            case 3:
                                mAnh4= downloadUri.toString();
                                break;
                            case 4:
                                mAnh5= downloadUri.toString();
                                break;
                        }

                        // Nếu đã upload hết ảnh, cập nhật đối tượng AnhSP lên Realtime Database
                        if (imageCount == 4) {
                            anhSP = new AnhSP(mMasp, mAnh1, mAnh2, mAnh3, mAnh4, mAnh5);
                            Toast.makeText(ThemSanPham.this, " Có "+ imageCount +" ảnh SP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Xử lý lỗi khi upload ảnh thất bại
                Toast.makeText(ThemSanPham.this, "Thêm ảnh thất bại", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error uploading image: " + e.getMessage());            }
        });
    }

    public void addsanphamAPI() {
        mMasp = edt_masp.getText().toString();
        String mTenSP = edt_tensp.getText().toString();
        int mSoLuong = Integer.parseInt(edt_soluong.getText().toString());
        float mDonGia = Float.parseFloat(edt_dongia.getText().toString());
        String mMota = edt_motasp.getText().toString();
        int mMaSV = maSV;
        int mXetDuyet = 1;// chưa xác nhận

        //Thêm ảnh sản phẩm lên firebase
        for (int i = 0; i < imgUri.size(); i++) {
            Uri imageUri = imgUri.get(i);
            addAnhSPFireBase( i, imageUri);
        }
        sanPham = new SanPham(mMasp, maTL, mTenSP, mAnh1, mDonGia, mSoLuong, mMota, mXetDuyet, mMaSV);

        //thêm dữ liệu vào dbSanpham
        APIService.apiService.PostSANPHAM(sanPham).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    addAnhSPAPI (anhSP); //Thêm ảnh sp lên API
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ThemSanPham.this, "Không thêm được sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAnhSPAPI(AnhSP anhSP1) {
        APIService.apiService.PostANHSANPHAM(anhSP1).enqueue(new Callback<List<AnhSP>>() {//Thêm ảnh sản phẩm
            @Override
            public void onResponse(Call<List<AnhSP>> call, Response<List<AnhSP>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ThemSanPham.this, "Thêm Ảnh thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ThemSanPham.this, MainActivity.class));
                    finish();
                }
            }
            @Override
            public void onFailure(Call<List<AnhSP>> call, Throwable t) {
                Toast.makeText(ThemSanPham.this, "Không thêm được ảnh lên API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        spinner = findViewById(R.id.planets_spinner);
        edt_masp = findViewById(R.id.edt_add_masp);
        edt_tensp = findViewById(R.id.edt_add_tensp);
        edt_soluong = findViewById(R.id.edt_add_soluongsp);
        edt_dongia = findViewById(R.id.edt_add_giabansp);
        edt_motasp = findViewById(R.id.edt_add_motasp);
        btn_addSanPham = findViewById(R.id.btn_addsanpham);
        themanh = findViewById(R.id.btn_themanhsp);
        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.add_imgAnhSP1));
        imageViews.add(findViewById(R.id.add_imgAnhSP2));
        imageViews.add(findViewById(R.id.add_imgAnhSP3));
        imageViews.add(findViewById(R.id.add_imgAnhSP4));
        imageViews.add(findViewById(R.id.add_imgAnhSP5));
    }
}
