package com.kurantsov.kmptest.newsitem

import android.content.Intent
import android.net.Uri
import com.kurantsov.kmptest.mvp.Navigator

class NewsItemRouter(private val navigator: Navigator) : NewsItemContract.Router {
    override fun toOriginal(url: String) {
        navigator.openActivity(
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(url)
            }
        )
    }
}