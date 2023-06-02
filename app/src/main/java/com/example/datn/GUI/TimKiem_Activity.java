package com.example.datn.GUI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.SanPhamAdapter;
import com.example.datn.Api.APIService;
import com.example.datn.Model.SanPham;
import com.example.datn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiem_Activity extends AppCompatActivity {
    private ImageView img_back;
    private EditText edit_tk;
    private RecyclerView RecyclerView_SP;
    private TextView txt_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        anhxa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit_tk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                APIService.apiService.TimKiemSP(edit_tk.getText().toString().trim()).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        if (response.isSuccessful()) {
                            txt_error.setVisibility(View.GONE);
                            SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(TimKiem_Activity.this, response.body());
                            RecyclerView_SP.setLayoutManager(new LinearLayoutManager(TimKiem_Activity.this, RecyclerView.VERTICAL, false));
                            RecyclerView_SP.setAdapter(sanPhamAdapter);
                        } else {
                            SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(TimKiem_Activity.this, response.body());
                            RecyclerView_SP.setLayoutManager(new LinearLayoutManager(TimKiem_Activity.this, RecyclerView.VERTICAL, false));
                            RecyclerView_SP.setAdapter(sanPhamAdapter);
                            txt_error.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void anhxa() {
        img_back = findViewById(R.id.img_back);
        edit_tk = findViewById(R.id.edit_tk);
        RecyclerView_SP = findViewById(R.id.RecyclerView_SP);
        txt_error = findViewById(R.id.txt_error);
    }
}