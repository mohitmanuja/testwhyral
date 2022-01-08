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


class WebViewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webView = findViewById(R.id.web_view)
        progressBar = findViewById(R.id.progress)
        val token = intent?.getStringExtra("token")


        init("http://stage.terrafin.tech:3000/")
    }


    private fun init(pageUrl: String) {
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
        setCookie(pageUrl,"12IJrIbr9qHc62KzJdoTXN5dAQw","eyJwYXNzcG9ydCI6eyJ1c2VyIjp7ImZ1bGxOYW1lIjoiTW9oaXQgTWFudWphIiwiZmlyc3ROYW1lIjoiTW9oaXQiLCJsYXN0TmFtZSI6Ik1hbnVqYSIsImVtYWlsIjoibWFudWphLm1vaGl0QGdtYWlsLmNvbSIsInBob3RvIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2lpUzJ1RnR4N2lYMjFUNWZWOGR4X2JSU1RhUi1qbkdVZGxVWTVLRGpJPXM5Ni1jIiwiYXV0aFRva2VuIjoiZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKdmNtZGhibWw2WVhScGIyNUpSQ0k2SWpRd05qRTNNak00TFRJMk9HRXROR016TkMxaU1qYzFMV1ExWldGalptWTJNams1TXlJc0ltNWhiV1VpT2lKTmIyaHBkQ0JOWVc1MWFtRWlMQ0pwWkNJNkltTXlZak15TkRGaExXWXhZV1V0TkdZeFpDMDROakEyTFRoa1l6UXdaamhrT0RRME15SXNJbUYxZEdodmNtbDBhV1Z6SWpwYklrOVNSMTlWVTBWU0lpd2lUMUpIWDBGRVRVbE9JbDBzSW1WdFlXbHNJam9pYldGdWRXcGhMbTF2YUdsMFFHZHRZV2xzTG1OdmJTSXNJbWxoZENJNk1UWXpPVEl3TnpVek1pd2ljM1ZpSWpvaWJXRnVkV3BoTG0xdmFHbDBRR2R0WVdsc0xtTnZiU0lzSW1wMGFTSTZJbU15WWpNeU5ERmhMV1l4WVdVdE5HWXhaQzA0TmpBMkxUaGtZelF3Wmpoa09EUTBNeUlzSW1semN5STZJbmRvZVhKaGJDSXNJbVY0Y0NJNk1UWXpPVEl4TVRFek1uMC5HdkVQejc0c1pBalU0VXBiZUNpS3pMVFBDUVFSa0VpMFBYVHRZUTVzSU80In19fQ==")
        webView.loadUrl(pageUrl)

    }
    private fun setCookie(pageUrl: String,sessionIdSign:String, sessionId:String) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.acceptCookie()
        cookieManager.setCookie(pageUrl,
            "whyral-session.id.sig=$sessionIdSign; Domain=stage.terrafin.tech")
        cookieManager.setCookie(pageUrl,
            "whyral-session.id=$sessionId; Domain=stage.terrafin.tech")
        cookieManager.acceptThirdPartyCookies(webView)

    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

}