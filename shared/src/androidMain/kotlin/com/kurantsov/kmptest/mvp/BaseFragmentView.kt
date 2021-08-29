package com.kurantsov.kmptest.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragmentView<View : BaseView, Presenter : BasePresenter<View>>(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId), BaseView, KoinComponent {
    private var _presenter: Presenter? = null
    protected val presenter: Presenter get() = requireNotNull(_presenter)

    private var _navigator: Navigator? = null
    protected val navigator: Navigator get() = requireNotNull(_navigator)

    protected val logger: Kermit by inject { parametersOf(this::class.simpleName) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _navigator = (context as NavigatorProvider).provide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        _presenter = createPresenter(arguments)
        if (_presenter == null) {
            logger.w { "Presenter wasn't created with arguments = $arguments" }
            activity?.onBackPressed()
            return null
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this as View)
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewVisible()
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewVisible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind(this as View)
        _presenter = null
    }

    override fun onDetach() {
        super.onDetach()
        _navigator = null
    }

    override fun showError(error: String?) {
        logger.d { "showError: $error" }
        error?.let {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }
    }

    abstract fun createPresenter(args: Bundle?): Presenter?
}