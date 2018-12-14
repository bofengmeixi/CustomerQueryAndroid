package com.zhuge.customerQuery.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.MyApplication;
import com.zhuge.customerQuery.model.bean.LoginInfo;
import com.zhuge.customerQuery.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpEngine {
    static HttpEngine httpEngine;
    static OkHttpClient okHttpClient;

    private HttpEngine() {
    }

    public static HttpEngine getInstance() {
        if (okHttpClient == null) {
            synchronized (HttpEngine.class) {
                if (okHttpClient == null)
                    okHttpClient = new OkHttpClient();
            }
        }
        if (httpEngine == null) {
            synchronized (HttpEngine.class) {
                if (httpEngine == null)
                    httpEngine = new HttpEngine();
            }
        }
        return new HttpEngine();
    }

    public static void httpGet(String url, String value, final CompanyGet companyGet) {
        MyApplication myApplication=MyApplication.getInstance();
        String username=myApplication.getUsername();
        String password=myApplication.getPassword();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Context context=MyApplication.getInstance().getApplicationContext();
            LoginInfo loginInfo=SharedPreferencesUtils.getLoginInfo(context);
            username=loginInfo.getUsername();
            password=loginInfo.getPassword();
            myApplication.setUsername(username);
            myApplication.setPassword(password);
        }

        String auth = username + ":" + password;
        auth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        auth = "Basic " + auth;
        OkHttpClient okHttpClient = new OkHttpClient();
        //url+="?"+key+"="+value;
        url += value;
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",auth)
                .get()//默认就是GET请求，可以不写
                //.addHeader("Authorization", "Basic MTIzNDU2OjEyMzQ1Ng==")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.d(TAG, "onFailure: ");
                e.printStackTrace();
                companyGet.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                //Log.d(TAG, "onResponse: " + response.body().string());
                //Log.e("response",response.body().string());
                companyGet.onResponse(call, response);
            }
        });
    }

    public static void httpPost(String url, String requestBody, final CompanyGet companyGet) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        //String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.d(TAG, "onFailure: " + e.getMessage());
                companyGet.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                companyGet.onResponse(call, response);
                /*Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());*/
            }
        });
    }

    public static void postForm(String url, String key, String value, CompanyGet companyGet) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(key, value)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.d(TAG, "onFailure: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("response", response.body().string());
               /* Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());*/
            }
        });
    }

    public void getCompanyByEmail(String email, CompanyGet companyGet) {
        httpGet(HttpUrl.COMPANY_BY_EMAIL, email, companyGet);
    }

    public void getCompanyByCompanyName(String companyName, CompanyGet companyGet) {
        httpGet(HttpUrl.COMPANY_BY_COMPANYNAME, companyName, companyGet);
    }

    public void getCompanyByAppId(String appId, CompanyGet companyGet) {
        httpGet(HttpUrl.COMPANY_BY_APPID, appId, companyGet);
    }

    public void getCompanyByCompanyId(String companyId, CompanyGet companyGet) {
        httpGet(HttpUrl.COMPANY_BY_COMPANYID, companyId, companyGet);
    }

    public void login(String username, String password, String url, final CompanyGet companyGet) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String auth = username + ":" + password;
        auth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        auth = "Basic " + auth;
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", auth)
                .get()//默认就是GET请求，可以不写
                //.addHeader("Authorization","Basic MTIzNDU2OjEyMzQ1Ng==")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.d(TAG, "onFailure: ");
                e.printStackTrace();
                companyGet.onFailure(call, e);

            }

            @Override
            public void onResponse(Call call, Response response) {
                companyGet.onResponse(call, response);

            }
        });
    }
}
