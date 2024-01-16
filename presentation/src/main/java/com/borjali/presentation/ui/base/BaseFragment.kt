package com.borjali.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

const val BUNDLE_ARGS = "BUNDLE_ARGS"

abstract class BaseFragment<out T : ViewDataBinding, E : ViewModel>(private val inflate: Inflate<T>) :
    Fragment() {

    @Inject
    protected lateinit var viewModel: E

    private lateinit var _binding: T

    val binding get() = _binding

    protected var dataStateChangeListener: DataStateChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflate.invoke(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR._all, viewModel)
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }
    abstract fun subscribeObservers()
    /**
     * Do an action on UI thread.
     */
    fun doOnUI(func: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch { func() }
    }

    /**
     * Do an action on IO thread.
     */
    fun doOnIO(func: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch { func() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateChangeListener = context as DataStateChangeListener
        } catch (_: ClassCastException) {
        }
    }

    override fun setArguments(args: Bundle?) {
        if (args != null) {
            super.setArguments(
                Bundle(args).apply {
                    putBundle(BUNDLE_ARGS, args) // Wrap the arguments as BUNDLE_ARGS
                }
            )
        } else {
            super.setArguments(null)
        }
    }
}
