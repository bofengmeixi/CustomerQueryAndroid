package com.zhuge.customerQuery.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.MyApplication;
import com.zhuge.customerQuery.R;
import com.zhuge.customerQuery.adapter.CompanyListAdapter;
import com.zhuge.customerQuery.listener.OnItemClickListener;
import com.zhuge.customerQuery.model.bean.CompanyInfo;
import com.zhuge.customerQuery.model.bean.LoginInfo;
import com.zhuge.customerQuery.presenter.MainPresenter;
import com.zhuge.customerQuery.presenter.MainPresenterImpl;
import com.zhuge.customerQuery.util.SharedPreferencesUtils;
import com.zhuge.customerQuery.view.MainView;

import java.util.List;

public class MainActivity extends BaseActivity implements MainView ,OnItemClickListener {

    /*private EditText etCustomerQuery;
    private Button btSearch;*/
    private RecyclerView rvCompanyList;
    private TextView tv_error;
    private MainPresenter mainPresenter;

    private CompanyListAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ZhugeSDK.getInstance().init(getApplicationContext());
        initView();
        initData();
    }

    private void initView(){
        /*etCustomerQuery=findViewById(R.id.et_query_condition);
        btSearch=findViewById(R.id.bt_search);*/
        rvCompanyList=findViewById(R.id.rv_company_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        rvCompanyList.setLayoutManager(layoutManager);
        rvCompanyList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        tv_error=findViewById(R.id.tv_error);
        progressBar=findViewById(R.id.pb_company_list);
    }

    private void initData(){
        Intent intent=getIntent();
        String email=intent.getStringExtra(Constants.EMAIL);
        String companyName=intent.getStringExtra(Constants.COMPANY_NAME);
        String appId=intent.getStringExtra(Constants.APP_ID);
        mainPresenter=new MainPresenterImpl(this);
        if (!TextUtils.isEmpty(companyName)){
            mainPresenter.getCompanyListByCompanyName(companyName);
        }else if (!TextUtils.isEmpty(email)){
            mainPresenter.getCompanyListByEmail(email);
        }else if (!TextUtils.isEmpty(appId)){
            mainPresenter.getCompanyListByAppId(appId);
        }

    }

    private boolean hasLoginInfo() {
        String username = MyApplication.getInstance().getUsername();
        String password = MyApplication.getInstance().getPassword();
        if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password))
            return true;
        LoginInfo loginInfo = SharedPreferencesUtils.getLoginInfo(getApplicationContext());
        if (loginInfo == null)
            return false;
        username = loginInfo.getUsername();
        password = loginInfo.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            return false;
        return true;
    }

    @Override
    public void showCustomerList(final List<CompanyInfo> companyList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (companyList==null||companyList.size()==0){
                    showNull();
                    return;
                }
                tv_error.setVisibility(View.GONE);
                rvCompanyList.setVisibility(View.VISIBLE);
                adapter=new CompanyListAdapter(companyList,MainActivity.this);
                rvCompanyList.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvCompanyList.setVisibility(View.GONE);
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText(error);
            }
        });
    }

    @Override
    public void showNull() {
        rvCompanyList.setVisibility(View.GONE);
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

    @Override
    public void onItemClick(String companyId) {
        if (!hasLoginInfo()){
            startActivity(new Intent(MainActivity.this,LoginInfo.class));

            finish();
        }
        Intent intent=new Intent(MainActivity.this,CompanyInfoActivity.class);
        intent.putExtra(Constants.COMPANY_ID,companyId);
        startActivity(intent);
    }
}
