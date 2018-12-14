package com.zhuge.customerQuery.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.zhuge.analysis.stat.ZhugeSDK;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#89cff0"));
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZhugeSDK.getInstance().startTrack(this.getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        ZhugeSDK.getInstance().endTrack(this.getLocalClassName(),null);
    }
}
