package com.test.myportfolio.ui.main.address

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.test.myportfolio.R
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.AddressModel
import com.test.myportfolio.databinding.FragmentDongBinding
import com.test.myportfolio.databinding.ItemAddressBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DongFragment : BaseBindingFragment<FragmentDongBinding, DongViewModel>(DongViewModel::class.java) {
    override val viewModel: DongViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_dong
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.dong
    override fun onBackEvent() { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAddress.apply {
            adapter = object : BaseRecyclerAdapter.Create<AddressModel.AddressModels.List, ItemAddressBinding>(
                R.layout.item_address,
                bindingVariableId =  BR.itemAddress,
                bindingListener = BR.addressItemListener
            ){
                override fun onItemClick(item: AddressModel.AddressModels.List, view: View) {
                    var str = ""
                    when(view.tag){
                        "우편번호" -> str = item.zipNo
                        "도로명 주소" -> str = item.lnmAdres
                        "번지 주소" -> str = item.rnAdres
                    }
                    (mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
                        setPrimaryClip(ClipData.newPlainText("label", str))
                        Toast.makeText(context,"${view.tag}를 복사했습니다",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}