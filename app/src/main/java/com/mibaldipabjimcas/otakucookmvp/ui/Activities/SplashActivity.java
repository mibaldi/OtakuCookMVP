package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    public static final int seconds = 4;
    public static final int delay = 1;
    public static  final int miliseconds= seconds * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.splash_image).into(imageView);
        progressBar.setMax(max_progress());
        startAnimation();
    }

    private int max_progress() {
        return seconds-delay;
    }

    public void startAnimation(){
        new CountDownTimer(miliseconds,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(set_progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }


    private int set_progress(long milisegundos) {
        return (int)((miliseconds-milisegundos)/1000);
    }

}
