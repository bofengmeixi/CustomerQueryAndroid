package com.zhuge.customerQuery.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.zhuge.analysis.stat.ZhugeSDK;
import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.MyApplication;
import com.zhuge.customerQuery.R;
import com.zhuge.customerQuery.model.bean.LoginInfo;

import com.zhuge.customerQuery.util.SharedPreferencesUtils;
import com.zhuge.customerQuery.util.ToastUtils;


public class ConditionActivity extends BaseActivity implements View.OnClickListener, View.OnKeyListener {

    private EditText etEmail;
    private EditText etCompanyName;
    private EditText etAppId;

    private Button btQuery;
    private TextView tvClear;

    private String email;
    private String companyName;
    private String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        initView();
        initListener();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                clearLoginInfo();
                startActivity(new Intent(ConditionActivity.this,LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearLoginInfo(){
        MyApplication.getInstance().setPassword(null);
        MyApplication.getInstance().setUsername(null);

        SharedPreferencesUtils.clearLoginInfo(getApplicationContext());
    }

    private void initView() {
        etEmail = findViewById(R.id.et_email);
        etCompanyName = findViewById(R.id.et_company_name);
        etAppId = findViewById(R.id.et_app_id);
        btQuery = findViewById(R.id.bt_query);
        tvClear = findViewById(R.id.bt_clear);
    }

    private void initData() {
        /*String did = DidUtils.getDid(getApplicationContext());

        ZhugeParam zhugeParam = new ZhugeParam.Builder()
                .appChannel("wechat")
                .appKey("583ff9526c5b4748aa9a435a1e3cb0e9")
                .did(did)
                .inAppDataListener(new ZhugeInAppDataListener() {
                    @Override
                    public void zgOnInAppDataReturned(JSONObject jsonObject) {
                        Log.e("zhugeAppData", jsonObject.toString());
                    }

                    @Override
                    public void zgOnFailed(String s) {
                        Log.e("failed", s);
                    }
                })
                .build();
        ZhugeSDK.getInstance().initWithParam(getApplicationContext(), zhugeParam);*/
        ZhugeSDK.getInstance().init(getApplicationContext());
        ZhugeSDK.getInstance().openDebug();
        //如果应用中没有存储登录信息则跳转到登录页面
        if (!hasLoginInfo()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private boolean hasLoginInfo() {
        MyApplication myApplication = MyApplication.getInstance();
        String username ;
        String password ;
        if (myApplication != null) {
            username = myApplication.getUsername();
            password = myApplication.getPassword();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
                return true;
        }

        LoginInfo loginInfo = SharedPreferencesUtils.getLoginInfo(getApplicationContext());
        if (loginInfo == null)
            return false;
        username = loginInfo.getUsername();
        password = loginInfo.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            return false;
        return true;
    }

    private void initListener() {
        etCompanyName.setOnKeyListener(this);
        etEmail.setOnKeyListener(this);
        etAppId.setOnKeyListener(this);
        btQuery.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_query:
                enterMainActivity();
                break;
            case R.id.bt_clear:
                etEmail.getText().clear();
                etCompanyName.getText().clear();
                etAppId.getText().clear();
                break;
        }
    }

    private void enterMainActivity() {
        email = etEmail.getText().toString();
        companyName = etCompanyName.getText().toString();
        appId = etAppId.getText().toString();
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(companyName) && TextUtils.isEmpty(appId)) {
            ToastUtils.showShortToast(getApplicationContext(), Constants.INPUT_WARNING);

            return;
        }
        Intent intent = new Intent(ConditionActivity.this, MainActivity.class);
        if (!TextUtils.isEmpty(email)) {
            intent.putExtra(Constants.EMAIL, email);
        } else if (!TextUtils.isEmpty(appId)) {
            intent.putExtra(Constants.APP_ID, appId);
        } else if (!TextUtils.isEmpty(companyName)) {
            intent.putExtra(Constants.COMPANY_NAME, companyName);
        }
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_company_name:
            case R.id.et_email:
            case R.id.et_app_id:
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    enterMainActivity();
                }
                break;
        }
        return false;
    }

    public void finishSelf() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZhugeSDK.getInstance().flush(getApplicationContext());
    }
}
