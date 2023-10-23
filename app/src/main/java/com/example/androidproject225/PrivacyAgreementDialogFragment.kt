package com.example.androidproject225

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.internal.artificialFrame
import org.json.JSONException
import org.json.JSONObject


class PrivacyAgreementDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_URL = "ARG_URL"
        private const val ARG_FULL_SCREEN = "ARG_FULL_SCREEN"

        fun newInstance(url: String, isFullScreen: Boolean): PrivacyAgreementDialogFragment {
            val args = Bundle()
            args.putString(ARG_URL, url)
            args.putBoolean(ARG_FULL_SCREEN, isFullScreen)

            val fragment = PrivacyAgreementDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var webView: WebView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_privacy_agreement, null)
        webView = view.findViewById(R.id.wb_load_privacy_agreement)
        setupWebView()

        val dialog = Dialog(requireContext(), R.style.FullScreenDialogStyle)
        dialog.setContentView(view)

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        loadWebPage()
        return dialog
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        webView.stopLoading()
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(this, "android")
        webView.webChromeClient = WebChromeClient()
    }

    private fun loadWebPage() {
        val url = arguments?.getString(ARG_URL)
        val isFullScreen = arguments?.getBoolean(ARG_FULL_SCREEN)
        Log.e("pLog", " ---- $url -------isFullScreen = $isFullScreen")
        if (url != null)
            webView.loadUrl(url)
    }


    /**
     * android端编写callJava函数，接收事件
     * @param method  方法名，见下方说明
     * @param dataJson json数据
     */
    @JavascriptInterface
    fun postMessage(method: String, dataJson: String?): String? {
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