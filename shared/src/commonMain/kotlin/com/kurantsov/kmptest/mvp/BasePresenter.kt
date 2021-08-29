package com.kurantsov.kmptest.mvp

import co.touchlab.kermit.Kermit
import kotlinx.coroutines.*

abstract class BasePresenter<V : BaseView>(
    protected val logger: Kermit
) {

    protected var view: V? = null
    protected val presenterScope: CoroutineScope = MainScope()

    fun bind(view: V) {
        this.view = view
        onBind(view)
    }

    fun unbind(view: V) {
        if (this.view == view) {
            presenterScope.coroutineContext.cancelChildren()
            this.view = null
            onUnbind()
        }
    }

    protected open fun onBind(view: V) {}
    open fun onViewVisible() {}
    open fun onViewInvisible() {}
    protected open fun onUnbind() {}

    fun executeWithProgress(block: suspend () -> Unit) {
        presenterScope.launch {
            view?.setLoading(true)
            try {
                block()
            } catch (e: CancellationException) {
            } catch (e: Exception) {
                logger.e(e) { "executeWithProgress: error during block execution" }
                view?.showError(e.message)
            }
            view?.setLoading(false)
        }
    }
}