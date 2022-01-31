package com.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.whyral.sample.R
import com.whyral.sdk.RewardFragment
import com.whyral.sdk.RewardUtils


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openActivity()
//        openRewardFragment()
    }

    private fun openActivity() {
        RewardUtils.startRewardFlow(
            this,
            "",
            ""
        )
        finish()
    }

    private fun openRewardFragment() {
        val rewardFragment = RewardFragment.newInstance(
            "",
            "",
            true
        )
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.add(R.id.container, rewardFragment, "RewardFragment")
        transaction.addToBackStack(null)
        transaction.commit()
    }
}