package com.zhuge.customerQuery.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.R;

import com.zhuge.customerQuery.presenter.LoginPresenter;
import com.zhuge.customerQuery.presenter.LoginPresenterImpl;
import com.zhuge.customerQuery.util.ToastUtils;
import com.zhuge.customerQuery.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    Button login;
    LoginPresenter loginPresenter;
    EditText etUsername;
    EditText etPassword;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        loginPresenter = new LoginPresenterImpl(getApplicationContext(), LoginActivity.this);
        login.setOnClickListener(this);
        progressBar = findViewById(R.id.pb_login);
    }

    @Override
    public void forwardActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, ConditionActivity.class));
                LoginActivity.this.finish();
            }
        });

    }

    @Override
    public void showError(final String errorInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.showShortToast(getApplicationContext(), errorInfo);
            }
        });
    }

    @Override
    public void showProgressBar() {
        //if (progressBar.getVisibility() == View.GONE)
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        //if (progressBar.getVisibility() == View.VISIBLE)
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtils.showShortToast(getApplicationContext(), Constants.WARNING_USERINFO_EMPTY);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                loginPresenter.login(username, password);
                break;
        }
    }
}
