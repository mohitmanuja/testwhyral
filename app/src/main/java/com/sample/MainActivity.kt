package com.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whyral.sdk.RewardUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
        RewardUtils.startRewardFlow(this,"")
    }
}