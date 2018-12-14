package com.zhuge.customerQuery.util;

import android.content.Context;
import android.widget.Toast;

import com.zhuge.customerQuery.Constants;

public class ToastUtils {

    public static void showShortToast(Context context,String message){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    public static void showLongToast(Context context,String message){
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }

}
