package com.example.androidproject225;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.http.SslError;
import android.os.Message;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChromeClients extends WebChromeClient {
    Activity activity;
    WebView webView;
    private String TAB = "ChromeClients";
    private int type;

    public ChromeClients(Activity activity, WebView webView, int type) {
        this.activity = activity;
        this.webView = webView;
        this.type = type;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (type != 1) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", (arg0, arg1) -> arg0.dismiss()).show();
        result.cancel();
        return true;
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebView tempWebView = new WebView(activity);
        tempWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (type == 1) {
                    XXEventUtils.openSystemBrowser(activity, request.getUrl());
                } else {
                    XXEventUtils.openWindow(activity, request.getUrl().toString(), type);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(tempWebView);
        resultMsg.sendToTarget();
                return true;
    }


}
