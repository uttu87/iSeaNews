package com.iseasoft.iseanews.api.base;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    public  LoggingInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
