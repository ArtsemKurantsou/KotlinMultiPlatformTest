package com.kurantsov.kmptest.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.kurantsov.kmptest.mvp.BaseActivityNavigator
import com.kurantsov.kmptest.mvp.Navigator
import com.kurantsov.kmptest.mvp.NavigatorProvider
import com.kurantsov.kmptest.news.NewsFragment


class MainActivity : AppCompatActivity(), NavigatorProvider {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, NewsFragment())
            }
        }
    }

    override fun provide(): Navigator {
        return BaseActivityNavigator(this, R.id.container)
    }
}
