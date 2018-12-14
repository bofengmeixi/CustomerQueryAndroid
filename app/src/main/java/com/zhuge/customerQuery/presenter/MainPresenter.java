package com.zhuge.customerQuery.presenter;

public interface MainPresenter extends BasePresenter {
    void getCompanyListByEmail(String email);
    void getCompanyListByCompanyName(String companyName);
    void getCompanyListByAppId(String appId);
}
