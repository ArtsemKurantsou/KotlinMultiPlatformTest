package com.kurantsov.kmptest.mvp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class BaseActivityNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int
) : Navigator {

    override fun openFragment(fragment: Fragment, addToBackStack: Boolean) {
        activity.supportFragmentManager.commit {
            replace(containerId, fragment)
            addToBackStack(fragment.tag)
        }
    }

    override fun openActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun <T : Activity> openActivity(
        activityClass: Class<T>,
        args: Bundle?
    ) {
        openActivity(
            Intent(activity, activityClass).apply {
                if (args != null) {
                    putExtras(args)
                }
            }
        )
    }
}