package com.test.myportfolio.ui.main.encyclopedia

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.test.myportfolio.BR
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.EncyclopediaModel
import com.test.myportfolio.databinding.FragmentEncyclopediaBinding
import com.test.myportfolio.databinding.ItemEncyclopediaBinding
import com.test.myportfolio.util.contentConvert
import com.test.myportfolio.util.titleConvert
import org.koin.android.viewmodel.ext.android.viewModel


class EncyclopediaFragment :
    BaseBindingFragment<FragmentEncyclopediaBinding, EncyclopediaViewModel>(EncyclopediaViewModel::class.java) {
    override fun onBackEvent() { }
    override val viewModel: EncyclopediaViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_encyclopedia
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.encyclopedia

    private val someCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEncyclopedia.apply {
            adapter = object : BaseRecyclerAdapter.Create<EncyclopediaModel.RS.Items, ItemEncyclopediaBinding>(
                R.layout.item_encyclopedia,
                topLyoutResId = R.layout.item_encyclopedia,
                bindingVariableId = BR.itemEncyclopedia,
                bindingListener = BR.encyclopediaItemListener,
                bindingViewModel = viewModel
            ){
                override fun onItemClick(item: EncyclopediaModel.RS.Items, view: View) {
                    if(item.viewType == 0){
                        dialogControl(item)
                    }
                }
            }
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.nextPage()
                    }
                }
            })
        }
        viewModel.searchEncyclopedia.addOnPropertyChangedCallback(someCallback)
    }


    fun dialogControl(item: EncyclopediaModel.RS.Items){
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.alert_encyclopedia, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.apply {
            this.findViewById<TextView>(R.id.txt_title).text = titleConvert(item.title, "네이버")
            this.findViewById<TextView>(R.id.txt_content).text = contentConvert(
                item.description,
                "네이버"
            )
            Glide.with(context).load(item.thumbnail).override(Target.SIZE_ORIGINAL).into(
                this.findViewById<ImageView>(
                    R.id.img_profile
                )
            )
            this.findViewById<Button>(R.id.btn_link).setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.searchEncyclopedia.removeOnPropertyChangedCallback(someCallback)
    }
}

