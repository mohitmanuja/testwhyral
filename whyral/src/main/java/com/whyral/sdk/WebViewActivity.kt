package com.whyral.sdk

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.whyral.sdk.Utils.getWebURL
import com.whyral.sdk.Utils.setCookie


class WebViewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    private var isDev: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webView = findViewById(R.id.web_view)
        progressBar = findViewById(R.id.progress)
        init()
    }

    private fun init() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.run {
                    view?.loadUrl(this.toString())
                }
                return true
            }

        }
        val userId = intent?.getStringExtra(USER_ID) ?: ""
        val authToken = intent?.getStringExtra(TOKEN) ?: ""
        setCookie(Utils.getSessionId(userId, authToken))
        isDev = intent?.getBooleanExtra(IS_DEV, false) ?: false
        webView.loadUrl(getWebURL(isDev))

    }

    private fun setCookie(sessionId: String) {
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            acceptCookie()
            setCookie(isDev, sessionId)
            acceptThirdPartyCookies(webView)
        }

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}