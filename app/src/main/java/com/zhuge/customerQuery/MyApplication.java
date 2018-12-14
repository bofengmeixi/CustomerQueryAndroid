package com.zhuge.customerQuery;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private String username;
    private String password;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
