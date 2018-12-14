package com.zhuge.customerQuery.model;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface CompanyGet {
    void onFailure(Call call, IOException ioException);
    void onResponse(Call call, Response response);
}
