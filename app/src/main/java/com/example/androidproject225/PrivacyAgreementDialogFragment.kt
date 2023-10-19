package com.example.androidproject225

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import org.json.JSONException
import org.json.JSONObject


class PrivacyAgreementDialogFragment : DialogFragment() {
    private var isFullScreen = false // 是否全屏标志
    var webView: WebView? = null
    private var jumpUrl = ""

    companion object {
        fun newInstance(isFullScreen: Boolean, loadUrl: String): PrivacyAgreementDialogFragment? {
            val fragment = PrivacyAgreementDialogFragment()
            fragment.isFullScreen = isFullScreen
            fragment.jumpUrl = loadUrl
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 设置透明背景
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        // 如果是全屏，设置无标题和无边框
        if (isFullScreen) {
            setStyle(
                DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen
            );
        }
        return inflater.inflate(R.layout.dialog_privacy_agreement, container, false);
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.wb_load_privacy_agreement)
        if (null == jumpUrl) dismissAllowingStateLoss()
        webView?.let {
            it.settings.javaScriptEnabled = true
            it.addJavascriptInterface(this, "android")
            it.webViewClient = WebViewClient()
            it.loadUrl(jumpUrl)
        }
    }

    /**
     * android端编写callJava函数，接收事件
     * @param method  方法名，见下方说明
     * @param dataJson json数据
     */
    @JavascriptInterface
    fun callJava(method: String, dataJson: String?): String? {
        Log.e("pLog", "callJava --- method --- $method ------ $dataJson")
        //根据method参数处理不同事件
        when (method) {
            "firstrecharge" -> {

            }

            "login" -> {

            }

            "register" -> {

            }

            "recharge" -> {

            }

            "openWindow" -> {

            }

            "closeWindow" -> {

            }

            "recharge" -> {

            }

            "getPackageName" ->              // 获取包名
                return getPackageName(dataJson)

            "setOrientation" ->              //切换横竖屏
                return setOrientation(dataJson)

            else -> Log.e("Tag", "callJava error, methon: $method")
        }
        // 有返回值时返回具体数据，没有时返回空字符串
        return ""
    }

    private fun getPackageName(dataJson: String?): String? {
        // 内部等待实现
        return context?.packageName
    }

    private fun getAndroidID(): String? {
        // 内部等待实现
        val androidId =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID);
        return androidId
    }

    private fun setOrientation(dataJson: String?): String? {
        val json = JSONObject(dataJson)
        val dir = json.getString("dir")
        activity?.let {
            it.runOnUiThread(Runnable {
                if (dir == "V") {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                } else {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }
            })
        }

        return ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
    }

    //复制到粘贴板
    fun copyToClipBoard(strJson: String?) {
        //insert code
        try {
            val json = JSONObject(strJson)
            val content = json.getString("content")
            requireActivity()?.let {
                it.runOnUiThread(Runnable {
                    val myClipboard: ClipboardManager =
                        it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    myClipboard.text = content
                })
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}