package com.example.datn.GUI;

import static com.example.datn.GUI.DangNhap_Activity.MASINHVIEN;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datn.Api.APIService;
import com.example.datn.Database.DbAnhSP;
import com.example.datn.Fragment.MainActivity;
import com.example.datn.Model.AnhSP;
import com.example.datn.Model.SanPham;
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
    private EditText edt_masp, edt_tensp, edt_soluong, edt_dongia, edt_motasp;
    private Button btn_addSanPham, themanh, danganh;
    List<ImageView> imageViews = new ArrayList<>();
    List<Uri> imgUri = new ArrayList<>();
    public static List<AnhSP> anhSPList;
    private static final int REQUEST_SELECT_IMAGES = 1;
    private SanPham sanPham;
    AnhSP anhSP ;
    String mAnh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);
        anhxa();
        onclick();
    }

    //Add ảnh lên firebase
    private void addAnhSP( final int imageCount, final Uri imageUri) {
        // Tạo tên của ảnh dựa trên số thứ tự của ảnh
        final String imageName = "anh" + (imageCount + 1);

        // Tham chiếu đến Firebase Storage
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(""+anhSP.getMaSP()).child(imageName);

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
                                anhSP.setAnh1(downloadUri.toString());
                                mAnh1 = anhSP.getAnh1();
                                break;
                            case 1:
                                anhSP.setAnh2(downloadUri.toString());
                                break;
                            case 2:
                                anhSP.setAnh3(downloadUri.toString());
                                break;
                            case 3:
                                anhSP.setAnh4(downloadUri.toString());
                                break;
                            case 4:
                                anhSP.setAnh5(downloadUri.toString());
                                break;
                        }

                        // Nếu đã upload hết ảnh, cập nhật đối tượng AnhSP lên Realtime Database
                        if (imageCount == 4) {
                            DatabaseReference anhSPRef = FirebaseDatabase.getInstance().getReference().child("anhSP").child(anhSP.getMaSP());
                            anhSPRef.setValue(anhSP);
                            Toast.makeText(ThemSanPham.this, "Thêm thành công ảnh sản phẩm", Toast.LENGTH_SHORT).show();
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
                // Get the selected image URI from the Intent
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    // Set the selected image to the first ImageView object
                    ImageView imageView = imageViews.get(0);
                    imgUri.add(imageUri);
                    imageView.setImageURI(imageUri);
                    // Hide any unused ImageView objects
                    for (int i = 1; i < imageViews.size(); i++) {
                        ImageView unusedImageView = imageViews.get(i);
                        unusedImageView.setVisibility(View.GONE);
                    }
                } else {
                    // Handle error if the user did not select an image
                    Toast.makeText(this, "Chưa có ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void onclick() {
        btn_addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addsanpham();
                addAnhSP();
                anhSPList.add(anhSP);
            }
        });

        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(galleryIntent, "Thêm ảnh"), REQUEST_SELECT_IMAGES);
            }
        });
    }

    public void addsanpham() {
        String mMaSP = edt_masp.getText().toString();
        String mTenSP = edt_tensp.getText().toString();
        int mSoLuong = Integer.parseInt(edt_soluong.getText().toString());
        float mDonGia = Float.parseFloat(edt_dongia.getText().toString());
        String mMota = edt_motasp.getText().toString();
        int mMaSV = MASINHVIEN;
        int mXetDuyet = 1;
        String mMaTL = "L01";
        sanPham = new SanPham(mMaSP, mMaTL, mTenSP, mAnh1, mDonGia, mSoLuong, mMota, mXetDuyet, mMaSV);

        //Thêm ảnh sản phẩm lên firebase
        anhSP.setMaSP(mMaSP);
        for (int i = 0; i < imgUri.size(); i++) {
            Uri imageUri = imgUri.get(i);
            addAnhSP( i, imageUri);
        }

        //thêm dữ liệu vào dbSanpham
        APIService.apiService.PostSANPHAM(sanPham).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != null) {
                    if (response.body() > 0) {
                        Toast.makeText(ThemSanPham.this, "Thêm ok rồi đấy", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void addAnhSP() {
        APIService.apiService.PostANHSANPHAM(anhSP).enqueue(new Callback<List<AnhSP>>() {//Thêm ảnh sản phẩm
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
            }
        });
    }

    private void anhxa() {
        edt_masp = findViewById(R.id.edt_add_masp);
        edt_tensp = findViewById(R.id.edt_add_tensp);
        edt_soluong = findViewById(R.id.edt_add_soluongsp);
        edt_dongia = findViewById(R.id.edt_add_giabansp);
        edt_motasp = findViewById(R.id.edt_add_motasp);
        btn_addSanPham = findViewById(R.id.btn_addsanpham);
        themanh = findViewById(R.id.btn_themanhsp);
        danganh = findViewById(R.id.btn_danganhsp);
        anhSP = new AnhSP();
        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.add_imgAnhSP1));
        imageViews.add(findViewById(R.id.add_imgAnhSP2));
        imageViews.add(findViewById(R.id.add_imgAnhSP3));
        imageViews.add(findViewById(R.id.add_imgAnhSP4));
        imageViews.add(findViewById(R.id.add_imgAnhSP5));
    }
}
