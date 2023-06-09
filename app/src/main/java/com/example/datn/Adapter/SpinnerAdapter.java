package com.example.datn.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datn.Model.TheLoai;
import com.example.datn.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<TheLoai> {

    private Context context;
    private List<TheLoai> theloaiSpinerList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_spiner,parent,false);
        TextView textView2 = convertView.findViewById(R.id.tv_selected);
        TheLoai theLoai = this.getItem(position);
        if (theLoai != null){
            textView2.setText(theLoai.getTenTL());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spiner,parent,false);
        TextView textView = convertView.findViewById(R.id.item_spiner_text);
        TheLoai theLoai = this.getItem(position);
        if (theLoai != null){
            textView.setText(theLoai.getTenTL());
        }
        return convertView;
    }

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<TheLoai> objects) {
        super(context, resource, objects);
    }
}