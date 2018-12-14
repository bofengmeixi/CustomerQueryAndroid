package com.zhuge.customerQuery.view;

import com.zhuge.customerQuery.model.bean.CompanyInfo;

import java.util.List;

public interface CompanyInfoView extends BaseView {
    void showAppList(List appList);
    void showError(String error);
    void showNull();

    void showProgressBar();
    void hideProgressBar();
}
