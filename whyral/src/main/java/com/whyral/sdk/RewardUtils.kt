package com.whyral.sdk

import android.content.Context
import android.content.Intent

const val TOKEN = "token"
const val USER_ID = "user_id"
const val IS_DEV = "is_dev"

object RewardUtils {
    fun startRewardFlow(context: Context, authToken: String, userId: String, isDev:Boolean = false) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.apply {
            putExtra(TOKEN, authToken)
            putExtra(USER_ID, userId)
            putExtra(IS_DEV, isDev)
        }
        context.startActivity(intent)
    }
}