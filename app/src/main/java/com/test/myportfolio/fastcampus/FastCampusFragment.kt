package com.test.myportfolio.fastcampus

import android.os.Bundle
import android.util.Log
import android.view.View
import com.test.myportfolio.R
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentFastcampusBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FastCampusFragment : BaseBindingFragment<FragmentFastcampusBinding, FastCampusViewModel>(FastCampusViewModel::class.java){
    override val viewModel: FastCampusViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_fastcampus
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.fastcampus

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("lys","onViewCreated")

    }

    override fun onStart() {
        super.onStart()
        Log.d("lys","onStart")

    }

    override fun onUiPause() {
        super.onUiPause()
        Log.d("lys","onUiPause")

    }

    override fun onUiResume() {
        super.onUiResume()
        Log.d("lys","onUiResume")

    }

    override fun onUiStart() {
        super.onUiStart()
        Log.d("lys","onUiStart")

    }

    override fun onUiStop() {
        super.onUiStop()
        Log.d("lys","onUiStop")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lys","onCreate")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lys","onDestroy")

    }
}