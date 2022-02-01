package com.whyral.sdk

import android.os.Build
import android.webkit.CookieManager
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

    fun getWebURL(isDev:Boolean): String {
        return if (isDev) WEB_URL_TEST else WEB_URL_PROD
    }

    fun CookieManager.setCookie(isDev: Boolean,sessionId:String) {
        if (isDev){
            setCookie(getWebURL(isDev), "whyral-session.id=$sessionId; Domain=stage.terrafin.tech")
        }else{
            setCookie(getWebURL(isDev), "whyral-session.id=$sessionId; Domain=www.acecredit.in")
        }
    }
}