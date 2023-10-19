package com.example.androidproject225;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        // 对 URL 变化进行处理
        Log.e("pLog", "监听网址变化------- " + url);
        Response response = chain.proceed(request);
        return response;
    }
}

