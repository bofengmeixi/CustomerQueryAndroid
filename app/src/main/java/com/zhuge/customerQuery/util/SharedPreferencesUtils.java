package com.zhuge.customerQuery.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.model.bean.LoginInfo;

public class SharedPreferencesUtils {
    static String name = Constants.SP_FILENAME;

    public static void putLoginInfo(Context context, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putString(Constants.SP_USERNAME, username)
                .putString(Constants.SP_PASSWORD, password)
                .commit();
    }

    public static LoginInfo getLoginInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String username=sharedPreferences.getString(Constants.SP_USERNAME,"");
        String password=sharedPreferences.getString(Constants.SP_PASSWORD,"");
        LoginInfo loginInfo=new LoginInfo(username,password);
        return loginInfo;
    }

    public static void clearLoginInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
