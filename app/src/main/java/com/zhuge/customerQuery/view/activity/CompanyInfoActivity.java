package com.zhuge.customerQuery.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.R;
import com.zhuge.customerQuery.adapter.AppListAdapter;
import com.zhuge.customerQuery.adapter.CompanyListAdapter;
import com.zhuge.customerQuery.presenter.CompanyInfoPresenter;
import com.zhuge.customerQuery.presenter.CompanyInfoPresenterImpl;
import com.zhuge.customerQuery.presenter.MainPresenter;
import com.zhuge.customerQuery.presenter.MainPresenterImpl;
import com.zhuge.customerQuery.view.CompanyInfoView;

import java.util.List;

public class CompanyInfoActivity extends BaseActivity implements CompanyInfoView {

    private RecyclerView rvAppList;
    private TextView tv_error;
    private CompanyInfoPresenter companyInfoPresenter;

    private AppListAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        initView();
        initData();
    }

    private void initView(){
        rvAppList=findViewById(R.id.rv_app_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        rvAppList.setLayoutManager(layoutManager);
        rvAppList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        tv_error=findViewById(R.id.tv_error);
        progressBar=findViewById(R.id.pb_company_info);
    }

    private void initData(){
        Intent intent=getIntent();
        String companyId=intent.getStringExtra(Constants.COMPANY_ID);
        companyInfoPresenter=new CompanyInfoPresenterImpl(this);
        companyInfoPresenter.getAppList(companyId);

    }

    @Override
    public void showAppList(final List appList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (appList==null||appList.size()==0){
                    showNull();
                    return;
                }
                tv_error.setVisibility(View.GONE);
                rvAppList.setVisibility(View.VISIBLE);
                adapter=new AppListAdapter(appList);
                rvAppList.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvAppList.setVisibility(View.GONE);
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText(error);
            }
        });
    }

    @Override
    public void showNull() {
        rvAppList.setVisibility(View.GONE);
        tv_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
