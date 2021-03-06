package com.test.myportfolio.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import com.test.myportfolio.BR

abstract class BaseBindingFragment<B : ViewDataBinding, M : BaseViewModel>(private val clazz: Class<M>) :
    BaseFragment(),
    LifecycleObserver {
    lateinit var binding: B

    abstract val viewModel: M

    abstract fun initData(): Boolean

    abstract fun setVariable(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, getResourceId(), container, false)
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (initData()) {
            binding.setVariable(setVariable(), viewModel)
            binding.setVariable(BR.main, sharedViewModel)
        }
        viewModel.navigatorFlag.observe(viewLifecycleOwner, {
            if(it != 0) {
                navigate(it)
                viewModel.navigatorFlag.value = 0
            }
        })
    }
}