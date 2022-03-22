package com.test.myportfolio.ui.main.papago

import android.os.Bundle
import android.view.View
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentPapagoBinding
import com.test.myportfolio.BR
import org.koin.android.viewmodel.ext.android.viewModel

class PapagoFragment : BaseBindingFragment<FragmentPapagoBinding, PapagoViewModel>(PapagoViewModel::class.java) {
    override fun getResourceId(): Int = R.layout.fragment_papago
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.papago
    override val viewModel: PapagoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}