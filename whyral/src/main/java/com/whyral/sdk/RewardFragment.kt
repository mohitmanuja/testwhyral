package com.whyral.sdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.whyral.sdk.Utils.getWebURL
import com.whyral.sdk.Utils.setCookie
import com.whyral.sdk.Utils.setWebClient


class RewardFragment : Fragment() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    private var isDev: Boolean = false

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
        isDev = arguments?.getBoolean(IS_DEV) ?: false
        val userId = arguments?.getString(USER_ID) ?: ""
        val authToken = arguments?.getString(TOKEN) ?: ""

        webView.setWebClient(
            context = requireContext(),
            onPageStarted = {
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
            },
            onPageFinished = {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        )
        webView.setCookie(isDev, Utils.getSessionId(userId, authToken))
        webView.loadUrl(getWebURL(isDev))
    }

    companion object {

        @JvmStatic
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