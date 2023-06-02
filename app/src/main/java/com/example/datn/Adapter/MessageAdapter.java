package com.example.datn.Adapter;

import static com.example.datn.GUI.DangNhap_Activity.MASINHVIEN;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.Model.Message;
import com.example.datn.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> mMessages;
    private String mSenderId;

    public MessageAdapter(Context context, List<Message> messages, String senderId) {
        mContext = context;
        mMessages = messages;
        mSenderId = senderId;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        if (message.getSenderId().equals(mSenderId)) {
            return MESSAGE_SENT;
        } else {
            return MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_message2, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bind(message);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessageText;
        private TextView mTimestamp;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_text);
            mTimestamp = itemView.findViewById(R.id.timestamp);
        }

        public void bind(Message message) {
            mMessageText.setText(message.getMessageText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String dateString = dateFormat.format(new Date(message.getTimestamp()));
            mTimestamp.setText(dateString);
        }
    }
}