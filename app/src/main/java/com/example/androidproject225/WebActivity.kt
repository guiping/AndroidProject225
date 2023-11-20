package com.example.androidproject225

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.lang.Exception
import java.util.Objects

class WebActivity : AppCompatActivity() {
    lateinit var web: WebView
    var isWsd = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        val webP = web.layoutParams as ViewGroup.LayoutParams
        webP.height = LayoutParams.MATCH_PARENT
        webP.width = LayoutParams.MATCH_PARENT
        setContentView(web)
//        AppCompatActivity
        val url = intent.getStringExtra("url")
        isWsd = intent.getIntExtra("isWsd", 0)
        if (TextUtils.isEmpty(url)) {
            finish()
        } else {
            initWebView(isWsd, url!!)
        }
    }

    private fun initWebView(isWsd: Int, url: String) {
        Log.e("pxLog", "initWebView : $url")
        web.loadUrl(url)
        val webSetting: WebSettings = web.settings
        webSetting.javaScriptEnabled = true
        webSetting.allowFileAccess = true
        webSetting.domStorageEnabled = true
        webSetting.setSupportMultipleWindows(true)
//        webSetting.userAgentString = XXEventUtils.getUseAgent(this,Constants.VERSION,)
        web.webChromeClient = ChromeClients(this, web, isWsd)
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        web.isHorizontalScrollBarEnabled = false
        web.isVerticalScrollBarEnabled = false
        if (isWsd == 1) {
            web.addJavascriptInterface(XXXJsAndroid(this), "Android")
        } else {
            web.addJavascriptInterface(object : Object() {
                @JavascriptInterface
                fun postMessage(tag: String, value: String) {
                    Log.e("pxLog", "jsBride ------ $tag  --- valued = $value")
                    if (XXEventUtils.needSendFlyerEvent(tag)) {
                        XXEventUtils.logEvent(isWsd, applicationContext, tag, value)
                    }
                    if (TextUtils.equals(tag, "openWindow")) {
                        try {
                            val jsonObj = JSONObject(value)
                            val url1 = jsonObj.optString("url")
                            XXEventUtils.openWindow(this@WebActivity, url1, isWsd)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    if (TextUtils.equals(tag, "closeWindow")) {

                    }
                }

            }, "jsBridge")
        }
    }
}