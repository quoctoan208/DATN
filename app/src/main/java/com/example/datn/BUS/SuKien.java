package com.example.datn.BUS;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.datn.R;

import java.text.DecimalFormat;

public class SuKien {

    private static Dialog dialog;
    public static DecimalFormat formatter = new DecimalFormat("###,###,###");

    public static void showDialog(Context context) {

        dialog = new Dialog(new ContextThemeWrapper(context,R.style.DialogAnimation));
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();

        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtb = window.getAttributes();
        windowAtb.gravity = Gravity.CENTER;
        window.setAttributes(windowAtb);
        dialog.show();
    }

    public static void dismissDialog(){
        dialog.dismiss();
    }

}
