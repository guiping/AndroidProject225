package com.example.androidproject225;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class XXXJsAndroid {
    private final Activity activity;
    private String TAG = "XXXJsAndroid";

    public XXXJsAndroid(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void openWebView(String url) {
        Log.e("pLog", "openWebView=  "  + url );
        XXEventUtils.openWindow(activity, url, 1);
    }

    @JavascriptInterface
    public void closeWebView() {
        Log.e("pLog", "closeWebView  "  );
        if (activity != null) {
            activity.finish();
        }
    }

    @JavascriptInterface
    public void openAndroid(String url) {

        Log.e("pLog", "openAndroid  " + url);
        XXEventUtils.openSystemBrowser(activity, Uri.parse(url));
    }

    @JavascriptInterface
    public void eventTracker(String eventType, String eventValues) {
        Log.e("pLog", "eventTracker  === eventType= " + eventType +"---eventValues =  " + eventValues);
        if (XXEventUtils.needSendWSDFlyerEvent(eventType)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> map = gson.fromJson(eventValues, type);
            Log.e(TAG, "eventTracker: " + map.toString());
            XXEventUtils.logEvent(1, activity.getApplicationContext(), eventType, eventValues);
        }
    }
}
