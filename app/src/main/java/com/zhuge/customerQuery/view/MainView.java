package com.zhuge.customerQuery.view;

import com.zhuge.customerQuery.model.bean.CompanyInfo;

import java.util.List;

public interface MainView extends BaseView {
    void showCustomerList(List<CompanyInfo> companyList);
    void showError(String error);
    void showNull();

    void showProgressBar();
    void hideProgressBar();
}
