package com.example.datn.BUS;

import android.view.animation.AnimationUtils;
import com.example.datn.R;
import android.content.Context;
public class Animation {
    static android.view.animation.Animation animation;

    public static void runAnimation(Context context){
        animation = AnimationUtils.loadAnimation(context,
                R.anim.slide_down);
    }
}
