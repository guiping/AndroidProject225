package com.example.androidproject225

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.appsflyer.AppsFlyerLib
import java.net.URL


class SplashActivity : AppCompatActivity() {
    var loadPb: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<TextView>(R.id.tv_quan).setOnClickListener {
//            showDialog()
        }
        loadPb = findViewById<ProgressBar>(R.id.pb_splash_load)
        initData(findViewById<WebView>(R.id.webview))
        initAf()
        findViewById<TextView>(R.id.tv_tanchuan).setOnClickListener {
//            showDialog()
        }
    }

    private fun showDialog(url: String,isFullScreen:Boolean) {
        val dialogFragment = PrivacyAgreementDialogFragment.newInstance(url,isFullScreen)
        dialogFragment.show(supportFragmentManager, "WebViewDialog")

    }

    val resultString = "https://i3tybz.shop/wap.html"

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData(webView: WebView) {
        loadPb?.visibility = View.VISIBLE
        webView.settings.javaScriptEnabled = true;
        webView.webViewClient = H5WebViewClient()
        webView.loadUrl(resultString)
    }
    private fun initAf(){
        val appsflyer = AppsFlyerLib.getInstance()
        appsflyer.setDebugLog(false)
        appsflyer.setMinTimeBetweenSessions(0)
        AppsFlyerLib.getInstance().setAppInviteOneLink("H5hv");
        appsflyer.init("",null,this)
        appsflyer.start(this)

    }

    private inner class H5WebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            loadPb?.visibility = View.GONE
            Log.e("pLog", "重载后的地址--- $url")
            url?.let {
                when (it) {
                    resultString -> { //不需要跳转到全屏
                        showDialog(url,false)
                    }

                    else -> { //全屏
                        showDialog(url,true)
                    }
                }
            }
        }
    }
}
