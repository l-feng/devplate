package com.example.core;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by liufeng on 2017/8/2.
 */

public class LoadingProgress extends Dialog {
    public LoadingProgress(Context context) {
        super(context);
    }

    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progress_loading_payment, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.progress_loading_payment);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.progress_loading_bar);

        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.cirle);
        animation.setInterpolator(new LinearInterpolator());
        spaceshipImage.startAnimation(animation);


        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;



    }
}
