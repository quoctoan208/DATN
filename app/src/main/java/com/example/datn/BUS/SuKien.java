package com.example.datn.BUS;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.datn.R;

import java.text.DecimalFormat;

public class SuKien {
    public static DecimalFormat formatter = new DecimalFormat("###,###,###");

    public static void SETDIALOG(Dialog dialog, int layout1) {
        dialog.setContentView(layout1);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams w = window.getAttributes();
        w.gravity = Gravity.CENTER;
        window.setAttributes(w);
        dialog.setCancelable(false);
    }

    public static void LOATDING(Dialog dialog) {
        SETDIALOG(dialog, R.layout.loading);
        dialog.show();
    }
}
