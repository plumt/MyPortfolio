package com.test.myportfolio.ui.main.encyclopedia

import android.app.Application
import androidx.databinding.ObservableField
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.EncyclopediaModel
import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EncyclopediaViewModel(
    application: Application,
    private val api: NaverApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    private var index = 1
    private var start = 1
    private var flag = false

    val encyclopediaItems = ListLiveData<EncyclopediaModel.RS.Items>()

    val searchEncyclopedia = ObservableField("")

    init {
        getEncyclopediaList()
        encyclopediaItems.add(EncyclopediaModel.RS.Items(0, 1, "", "", "", ""))
    }

    private fun getEncyclopediaList() {
        if (searchEncyclopedia.get()!!.trim().isEmpty()) return
        api.encyclopedia(
            mContext.getString(R.string.CLIENT_ID),
            mContext.getString(R.string.CLIENT_SERVICE),
            searchEncyclopedia.get()!!.trim(),
            start
        ).observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.items != null) {
                    io.reactivex.rxjava3.core.Observable.just(it)
                } else {
                    io.reactivex.rxjava3.core.Observable.empty()
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .map {
                it.items!!.forEach { encyclopediaList ->
                    var equals = true
                    encyclopediaItems.value!!.forEach {
                        if (it.link == encyclopediaList.link) {
                            equals = false
                        }
                    }
                    if (equals) {
                        encyclopediaItems.add(
                            EncyclopediaModel.RS.Items(
                                index++,
                                0,
                                encyclopediaList.title,
                                encyclopediaList.link,
                                encyclopediaList.thumbnail,
                                encyclopediaList.description
                            )
                        )
                    }
                }
            }.subscribe({
                flag = true
            }, {
                flag = true
                it.printStackTrace()
            })
    }

    fun nextPage() {
        if (flag) {
            flag = false
            start += 20
            getEncyclopediaList()
        }
    }

    fun textClear() {
        searchEncyclopedia.set("")
    }


    fun searchBtn() {
        index = 1
        reset()
        getEncyclopediaList()
    }

    private fun reset() {
        start = 1
        flag = true
        encyclopediaItems.clear(true)
        encyclopediaItems.add(EncyclopediaModel.RS.Items(0, 1, "", "", "", ""))
    }
}