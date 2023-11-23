package com.gwieolsd.gwoemove

import android.annotation.SuppressLint
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initData(findViewById<WebView>(R.id.webview))
        initAf()

    }

    private fun showDialog(url: String,isFullScreen:Boolean) {
        val dialogFragment = PrivacyAgreementDialogFragment.newInstance(url,isFullScreen)
        dialogFragment.show(supportFragmentManager, "ShowPrivacyDialog")
    }
    val resultString = "https://yirtwre.top/wap.html"
    @SuppressLint("SetJavaScriptEnabled")
    private fun initData(webView: WebView) {
        webView.settings.javaScriptEnabled = true;
        webView.webViewClient = H5WebViewClient()
        webView.loadUrl(resultString)
    }
    private fun initAf(){
        val appsflyer = AppsFlyerLib.getInstance()
        appsflyer.setDebugLog(false)
        appsflyer.setMinTimeBetweenSessions(0)
        AppsFlyerLib.getInstance().setAppInviteOneLink("H5hv");
        appsflyer.init("yV2srBcYyBvPBhWTYeNRoU",null,this)
        appsflyer.start(this)
    }

    private inner class H5WebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            url?.let {
                when (it) {
                    resultString -> { //不需要跳转到全屏
                        Intent(this@SplashActivity, MainActivity::class.java).apply {
                            startActivity(this)
                            this@SplashActivity.finish()
                        }
                    }
                    else -> { //全屏
                        showDialog(url,true)
                    }
                }
            }
        }
    }
}
