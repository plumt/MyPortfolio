package com.test.myportfolio.ui.main.address

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentAddressBinding
import org.koin.android.viewmodel.ext.android.viewModel
import com.test.myportfolio.BR
import com.google.android.material.tabs.TabLayoutMediator


class AddressFragment :
    BaseBindingFragment<FragmentAddressBinding, AddressViewModel>(AddressViewModel::class.java) {
    override val viewModel: AddressViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_address
    override fun initData(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.address

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpAddress.run{
            isUserInputEnabled = false
            adapter = object : FragmentStateAdapter(this@AddressFragment){
                override fun getItemCount(): Int = 2
                override fun createFragment(position: Int): Fragment {
                    return when(position){
                        0 -> RoadFragment()
                        1 -> DongFragment()
                        else -> Fragment()
                    }
                }
            }
        }

        binding.tabLayout.apply {
            TabLayoutMediator(this, binding.vpAddress) { tab, position ->
                when (position) {
                    0 -> tab.text = "도로명"
                    1 -> tab.text = "번지"
                }
            }.attach()
        }
    }
}

