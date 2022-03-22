package com.test.myportfolio.ui.main.papago

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.databinding.FragmentPapagoHistoryBinding
import org.koin.android.viewmodel.ext.android.viewModel
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.PapagoHistoryModel
import com.test.myportfolio.databinding.ItemPapagoHistoryBinding

class PapagoHistoryFragment : BaseBindingFragment<FragmentPapagoHistoryBinding, PapagoHistoryViewModel>(PapagoHistoryViewModel::class.java) {
    override val viewModel : PapagoHistoryViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_papago_history
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.papagoHistory

    // Item의 클릭 상태를 저장할 array 객체
    val selectedItems = SparseBooleanArray()

    // 직전에 클릭됐던 Item의 position
    var prePosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPapagoHistory.apply {
            adapter = object : BaseRecyclerAdapter.Create<PapagoHistoryModel, ItemPapagoHistoryBinding>(
                R.layout.item_papago_history,
                bindingVariableId = BR.itemPapagoHistory,
                bindingListener = BR.papagoHistoryItemListener
            ){
                override fun onItemClick(item: PapagoHistoryModel, view: View) {
                    if (selectedItems.get(item.id)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(item.id)
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition)
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(item.id, true)
                    }
//                    selected.onNext(selectedItems.size() != 0)
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition)
                    notifyItemChanged(item.id)
                    // 클릭된 position 저장
                    prePosition = item.id
                }

                override fun onBindViewHolder(
                    holder: BaseRecyclerAdapter.BaseViewHolder<ItemPapagoHistoryBinding>,
                    position: Int
                ) {
                    super.onBindViewHolder(holder, position)
                    if(holder.binding is ItemPapagoHistoryBinding){
                        holder.binding.txtValue.visibility = if(selectedItems.get(position)) View.VISIBLE else View.GONE
                    }
                }
            }
        }

    }
}