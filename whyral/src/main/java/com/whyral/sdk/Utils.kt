package com.whyral.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject
import java.util.*

const val WEB_URL_TEST = "http://stage.terrafin.tech:3000/"
const val WEB_URL_PROD = "https://www.acecredit.in:444/"
object Utils {

    fun getSessionId(userId: String, authToken: String): String {
        val json = JSONObject().apply {
            val userObject = JSONObject().apply {
                put("authToken", authToken)
                put("userID", userId)
            }
            put("passport", JSONObject().put("user", userObject))
        }
        return json.toString().encode()
    }

    private fun String.encode(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(this.toByteArray())
        } else {
            android.util.Base64.encodeToString(this.toByteArray(), android.util.Base64.DEFAULT);
        }
    }

    fun getWebURL(isDev: Boolean): String {
        return if (isDev) WEB_URL_TEST else WEB_URL_PROD
    }

    fun CookieManager.setCookie(isDev: Boolean, sessionId: String) {
        if (isDev) {
            setCookie(getWebURL(isDev), "whyral-session.id=$sessionId; Domain=stage.terrafin.tech")
        } else {
            setCookie(getWebURL(isDev), "whyral-session.id=$sessionId; Domain=www.acecredit.in")
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    fun WebView.setWebClient(context: Context, onPageStarted:()->(Unit), onPageFinished: () -> Unit){
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                onPageStarted.invoke()
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                onPageFinished.invoke()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.d("<<TAG>>", "shouldOverrideUrlLoading: "+url)
                if (URLUtil.isNetworkUrl(url)) {
                    return false
                } else {
                    return try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        }
    }
    fun WebView.setCookie(isDev: Boolean,sessionId: String) {
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            acceptCookie()
            setCookie(isDev, sessionId)
            acceptThirdPartyCookies(this@setCookie)
        }
    }
}