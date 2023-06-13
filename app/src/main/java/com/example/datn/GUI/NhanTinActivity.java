package com.example.datn.GUI;

import static com.example.datn.GUI.ChiTietSP_Activity.maSV_SP;
import static com.example.datn.GUI.DangNhap_Activity.maSV;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Adapter.MessageAdapter;
import com.example.datn.Model.Message;
import com.example.datn.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NhanTinActivity extends AppCompatActivity {

    private static final String TAG = "NhanTinActivity";
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private List<Message> mMessages = new ArrayList<>();
    private EditText mMessageText;// Khai báo các biến UI
    private EditText mMessageEditText;
    private Button mSendButton;
    private String mSenderId;
    private String mReceiverId = maSV_SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhantin);

        // Khởi tạo Firebase Auth và Firebase Database
        String mAuth = maSV + "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Nếu đã đăng nhập, lấy ID của người dùng hiện tại và ID của người nhận
        mSenderId = mAuth;
        mReceiverId = "Người nhận";

        // Khởi tạo RecyclerView và MessageAdapter
        mRecyclerView = findViewById(R.id.message_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(this, mMessages, mSenderId);
        mRecyclerView.setAdapter(mAdapter);

        // Lấy danh sách các tin nhắn từ Firebase Database
        mDatabase.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessages.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if ((message.getSenderId().equals(mSenderId) && message.getReceiverId().equals(mReceiverId))
                            || (message.getSenderId().equals(mReceiverId) && message.getReceiverId().equals(mSenderId))) {
                        mMessages.add(message);
                    }
                }
                mAdapter.notifyDataSetChanged();
                int targetPosition = mAdapter.getItemCount() - 1;
                if (targetPosition >= 0) {
                    mRecyclerView.smoothScrollToPosition(targetPosition);
                } else {
                    // Xử lý lỗi khi danh sách tin nhắn rỗng ở đây
                    Toast.makeText(NhanTinActivity.this, "Chưa có tin nhắn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
            }
        });

        // Khởi tạo các view và set onClickListener cho nút gửi tin nhắn
        mMessageEditText = findViewById(R.id.message_edit_text);
        mSendButton = findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = mMessageEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(messageText)) {
                    long timestamp = System.currentTimeMillis();
                    Message message = new Message(mSenderId, mReceiverId, messageText, timestamp);
                    mDatabase.child("messages").push().setValue(message);
                    mMessageEditText.setText("");
                }
            }
        });
    }
}
