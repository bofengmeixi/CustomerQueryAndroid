package com.zhuge.customerQuery.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.model.CompanyGet;
import com.zhuge.customerQuery.model.HttpEngine;
import com.zhuge.customerQuery.model.bean.AppInfo;
import com.zhuge.customerQuery.view.CompanyInfoView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class CompanyInfoPresenterImpl implements CompanyInfoPresenter {

    private CompanyInfoView companyInfoView;
    private List<AppInfo> appList;
    private Gson gson;
    public CompanyInfoPresenterImpl(CompanyInfoView companyInfoView){
        this.companyInfoView=companyInfoView;
    }

    @Override
    public void getAppList(String companyId) {
        companyInfoView.showProgressBar();
        HttpEngine.getInstance().getCompanyByCompanyId(companyId, new CompanyGet() {
            @Override
            public void onFailure(Call call, IOException ioException) {
                companyInfoView.hideProgressBar();
                //companyInfoView.showError(ioException.toString());
                companyInfoView.showError(Constants.NETWORK_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) {
                companyInfoView.hideProgressBar();
                try {
                    String content=response.body().string();
                    if (gson==null)
                        gson=new Gson();
                    appList=gson.fromJson(content,new TypeToken<List<AppInfo>>(){}.getType());
                    companyInfoView.showAppList(appList);
                } catch (IOException e) {
                    e.printStackTrace();
                    companyInfoView.showError(e.toString());

                }
            }
        });
    }
}
