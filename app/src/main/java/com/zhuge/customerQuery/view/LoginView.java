package com.zhuge.customerQuery.view;

public interface LoginView extends BaseView {
    void forwardActivity();
    void showError(String errorInfo);

    void showProgressBar();
    void hideProgressBar();
}
