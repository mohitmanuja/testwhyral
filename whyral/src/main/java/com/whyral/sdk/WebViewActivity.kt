package com.whyral.sdk

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.whyral.sdk.Utils.getWebURL
import com.whyral.sdk.Utils.setCookie
import com.whyral.sdk.Utils.setWebClient


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

    private fun init(){
        isDev = intent?.getBooleanExtra(IS_DEV, false) ?: false
        val userId = intent?.getStringExtra(USER_ID) ?: ""
        val authToken = intent?.getStringExtra(TOKEN) ?: ""

        webView.setWebClient(
            context = this,
            onPageStarted = {
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
            },
            onPageFinished = {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        )

        webView.setCookie(isDev,Utils.getSessionId(userId, authToken))
        webView.loadUrl(getWebURL(isDev))
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}