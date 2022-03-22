package com.test.myportfolio.ui.main.firebase

import android.os.Bundle
import android.view.View
import com.test.myportfolio.R
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentFirebaseBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FirebaseFragment :
BaseBindingFragment<FragmentFirebaseBinding, FirebaseViewModel>(FirebaseViewModel::class.java){
    override val viewModel: FirebaseViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_firebase
    override fun initData(): Boolean = true
    override fun onBackEvent() {
    }

    override fun setVariable(): Int = BR.firebase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}