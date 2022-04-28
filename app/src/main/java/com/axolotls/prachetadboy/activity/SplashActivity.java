package com.axolotls.prachetadboy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.helper.Constant;
import com.axolotls.prachetadboy.helper.Session;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        setContentView(R.layout.activity_splash);

        final Session session = new Session(getApplicationContext());

        int SPLASH_TIME_OUT = 2000;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (session.isUserLoggedIn()) {
                    session.setData(Constant.OFFSET, "" + 0);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        }, SPLASH_TIME_OUT);
    }
}