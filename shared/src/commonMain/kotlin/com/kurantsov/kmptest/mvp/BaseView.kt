package com.kurantsov.kmptest.mvp

interface BaseView {
    fun setLoading(isLoading: Boolean)
    fun showError(error: String?)
}