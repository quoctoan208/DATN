package com.example.datn.Database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.datn.Model.AnhSP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class DbAnhSP {
    private FirebaseDatabase database;
    private DatabaseReference AnhSPRef;
    private StorageReference storeRef;
    private List<AnhSP> listAnhSP;
    List<String> uriList = new ArrayList<>();
    String anh1,anh2,anh3,anh4,anh5;

    public DbAnhSP() {
        database = FirebaseDatabase.getInstance();
        AnhSPRef = database.getReference("ANHSP");
        storeRef = FirebaseStorage.getInstance().getReference();
        listAnhSP = new ArrayList<>();
    }
    public void upfullAnhSP(Activity activity, String masp){
        if (uriList.size()==0){
            anh1 = uriList.get(0);
        } else if (uriList.size()==1) {
            anh1 = uriList.get(0);
            anh2 = uriList.get(1);
        } else if (uriList.size()==2) {
            anh1 = uriList.get(0);
            anh2 = uriList.get(1);
            anh3 = uriList.get(2);
        }else if (uriList.size()==3){
            anh1 = uriList.get(0);
            anh2 = uriList.get(1);
            anh3 = uriList.get(2);
            anh4 = uriList.get(3);
        } else if (uriList.size()==4) {
            anh1 = uriList.get(0);
            anh2 = uriList.get(1);
            anh3 = uriList.get(2);
            anh4 = uriList.get(3);
            anh5 = uriList.get(4);
        }

        AnhSP anhSP = new AnhSP(masp,anh1,anh2,anh3,anh4,anh5);
        AnhSPRef.child(String.valueOf(anhSP.getMaSP())).setValue(anhSP);
        Toast.makeText(activity, "Thêm db ảnh thành công!", Toast.LENGTH_SHORT).show();
    }
    public void upAnhSP(Activity activity, String masp, List<Uri> uris) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        for (int i = 0; i < uris.size() ; i++){

            Uri imageUri = uris.get(i);
            StorageReference fileRef = storeRef.child(masp.toLowerCase() + "." +getFileExtension(imageUri,activity));
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uriList.add(uri.toString());
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(activity, "không upload được ảnh!", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            });
        }
    }
    private String getFileExtension(Uri mUri1, Activity activity){
        ContentResolver cr = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri1));
    }


    public List<AnhSP> getListAnhSP() {
        AnhSPRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAnhSP.clear();
                for (DataSnapshot snap: snapshot.getChildren()){
                    AnhSP anhSP = snap.getValue(AnhSP.class);
                    listAnhSP.add(anhSP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listAnhSP;
    }
}
