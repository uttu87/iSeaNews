package com.iseasoft.iseanews.api.base;

import android.app.ProgressDialog;
import android.content.Context;

import com.iseasoft.iseanews.interfaces.HttpCallback;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public abstract class BaseOkHttp implements Callback {
    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient getOkHttpClient(){

        if(okHttpClient == null){
            okHttpClient = new OkHttpClient
                    .Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor())
                    .build();
        }

        return  okHttpClient;
    }

    private boolean wantDialogCancelable = true;
    private boolean wantShowDialog = true;
    private Context context;
    private ProgressDialog progress;
    private String title = "";
    private String message = "Message...";
    private HttpCallback httpCallback;

}
