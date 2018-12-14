package com.zhuge.customerQuery.presenter;

import android.content.Context;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.MyApplication;
import com.zhuge.customerQuery.model.CompanyGet;
import com.zhuge.customerQuery.model.HttpEngine;
import com.zhuge.customerQuery.model.HttpUrl;
import com.zhuge.customerQuery.util.SharedPreferencesUtils;
import com.zhuge.customerQuery.view.LoginView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView loginView;
    Context context;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void login(final String username, final String password) {
        HttpEngine.getInstance().login(username, password, HttpUrl.LOGIN, new CompanyGet() {
            @Override
            public void onFailure(Call call, IOException ioException) {
                String e = ioException.toString();
                if (e.contains("TimeoutException")) {
                    loginView.showError(Constants.WARNING_TIME_OUT);
                } else if (e.contains("ConnectException")) {
                    loginView.showError(Constants.WARNING_REQUEST_FAILED);
                } else {
                    loginView.showError(Constants.WARNING_USERINFO_ERROR);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                String content = "";
                try {
                    content = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ("OK".equals(content)) {
                    MyApplication.getInstance().setUsername(username);
                    MyApplication.getInstance().setPassword(password);
                    SharedPreferencesUtils.putLoginInfo(context, username, password);
                    loginView.forwardActivity();
                } else loginView.showError(Constants.WARNING_USERINFO_ERROR);

            }
        });
    }
}
