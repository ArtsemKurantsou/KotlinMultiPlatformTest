package com.kurantsov.kmptest.mvp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

interface Navigator {
    fun openActivity(intent: Intent)
    fun <T : Activity> openActivity(activityClass: Class<T>, args: Bundle? = null)
    fun openFragment(fragment: Fragment, addToBackStack: Boolean = true)
}