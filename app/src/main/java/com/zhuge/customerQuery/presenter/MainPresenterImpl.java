package com.zhuge.customerQuery.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.model.CompanyGet;
import com.zhuge.customerQuery.model.HttpEngine;
import com.zhuge.customerQuery.model.bean.CompanyInfo;
import com.zhuge.customerQuery.view.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainPresenterImpl implements MainPresenter {

    MainView mainView;
    List<CompanyInfo> companyList = new ArrayList();
    Gson gson;

    public MainPresenterImpl(MainView mainView){
        this.mainView=mainView;
    }

    @Override
    public void getCompanyListByEmail(String email) {
        mainView.showProgressBar();
        HttpEngine.getInstance().getCompanyByEmail(email, new CompanyGet() {
            @Override
            public void onFailure(Call call, IOException ioException) {
                failure(ioException);
            }

            @Override
            public void onResponse(Call call, Response response) {
                response(response);
            }
        });
    }

    @Override
    public void getCompanyListByCompanyName(String companyName) {
        mainView.showProgressBar();
        HttpEngine.getInstance().getCompanyByCompanyName(companyName, new CompanyGet() {
            @Override
            public void onFailure(Call call, IOException ioException) {
                failure(ioException);
            }

            @Override
            public void onResponse(Call call, Response response) {
                response(response);
            }
        });
    }

    @Override
    public void getCompanyListByAppId(String appId) {
        mainView.showProgressBar();
        HttpEngine.getInstance().getCompanyByAppId(appId, new CompanyGet() {
            @Override
            public void onFailure(Call call, IOException ioException) {
                failure(ioException);
            }

            @Override
            public void onResponse(Call call, Response response) {
                response(response);
            }
        });
    }


    private void failure(IOException ioException){
        mainView.hideProgressBar();
        ioException.printStackTrace();
        //mainView.showError(ioException.toString());
        mainView.showError(Constants.NETWORK_FAILED);
    }

    private void response(Response response){
        mainView.hideProgressBar();
        try {
            String content=response.body().string();
            if (gson==null)
                gson=new Gson();
            companyList=gson.fromJson(content,new TypeToken<List<CompanyInfo>>(){}.getType());
            mainView.showCustomerList(companyList);
        } catch (Exception e) {
            e.printStackTrace();
            mainView.showError(e.toString());
        }
    }
}
