package com.whyral.sdk

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment


class RewardFragment : Fragment() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.web_view)
        progressBar = view.findViewById(R.id.progress)
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


        }
        val userId = arguments?.getString(USER_ID) ?: ""
        val authToken = arguments?.getString(TOKEN) ?: ""
        setCookie(Utils.getSessionId(userId, authToken))
        webView.loadUrl(WEB_URL)

    }

    private fun setCookie(sessionId: String) {
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            acceptCookie()
            val isDev = arguments?.getBoolean(IS_DEV)?:false
            if (isDev){
                setCookie(WEB_URL, "whyral-session.id=$sessionId; Domain=stage.terrafin.tech")
            }else{
                setCookie(WEB_URL, "whyral-session.id=$sessionId; Domain=www.acecredit.in")
            }
            acceptThirdPartyCookies(webView)
        }
    }

    companion object {
        fun newInstance(authToken: String, userId: String, isDev: Boolean): RewardFragment {
            val args = Bundle()
            args.putString(TOKEN, authToken)
            args.putString(USER_ID, userId)
            args.putBoolean(IS_DEV, isDev)
            val fragment = RewardFragment()
            fragment.arguments = args
            return fragment
        }

    }

}