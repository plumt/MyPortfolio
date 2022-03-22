package com.test.myportfolio.ui.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.HomeModel
import com.test.myportfolio.databinding.ItemHomeBinding

class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class.java) {
    override fun onBackEvent() { }
    override val viewModel: HomeViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvHome.apply {
            adapter = object : BaseRecyclerAdapter.Create<HomeModel, ItemHomeBinding>(
                R.layout.item_home,
                bindingVariableId = BR.itemHome,
                bindingListener = BR.homeItemListener,
                bindingViewModel = viewModel
            ) {
                override fun onItemClick(item: HomeModel, view: View) {
                    sharedViewModel.finish = false
                    when(item.title){
                        "영화" -> navigate(R.id.action_homeFragment_to_navigation_movie)
                        "사전" -> navigate(R.id.action_homeFragment_to_encyclopediaFragment)
                        "파파고" -> navigate(R.id.action_homeFragment_to_papagoFragment)
                        "주소" -> navigate(R.id.action_homeFragment_to_addressFragment)
                        "달력" -> navigate(R.id.action_homeFragment_to_calendarFragment)
                        "블루투스" -> navigate(R.id.action_homeFragment_to_bluetoothFragment)
                        "패스트 캠퍼스" -> navigate(R.id.action_homeFragment_to_fastCampusFragment)
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true) {
            /**
             * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
             */
            override fun handleOnBackPressed() {
                if(!sharedViewModel.finish) {
                    Toast.makeText(context, "앱을 종료하시려면 한 번 더 눌러주세요", Toast.LENGTH_SHORT).show()
                }
                sharedViewModel.finish = true
            }

        })

    }
}