package com.example.androidproject225

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<TextView>(R.id.tv_quan).setOnClickListener {
            showDialog()
        }
        initData()
        findViewById<TextView>(R.id.tv_tanchuan).setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val dialogFragment: PrivacyAgreementDialogFragment? =
            PrivacyAgreementDialogFragment.newInstance(
                false,
                "https://api.gilet.ceshi.in/testwsd.html"
            ) // 设置为非全屏
        dialogFragment?.show(fragmentManager, "dialog")
    }

    private fun initData() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(UrlInterceptor())
            .build()

// 构建网络请求
        val request: Request = Request.Builder()
            .url("https://i3tybz.shop/wap.html")
            .build()
// 发起网络请求
        client.newCall(request).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })

    }
}