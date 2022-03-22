package com.test.myportfolio.ui.main.home

import android.app.Application
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.HomeModel
import com.test.myportfolio.util.PreferenceManager


class HomeViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val homeItems = ListLiveData<HomeModel>()

    init {
        homeItems.add(HomeModel(0, 0, "영화"))
        homeItems.add(HomeModel(1, 0, "사전"))
        homeItems.add(HomeModel(2, 0, "파파고"))
        homeItems.add(HomeModel(3, 0, "주소"))
        homeItems.add(HomeModel(4, 0, "달력"))
        homeItems.add(HomeModel(5, 0, "블루투스"))
        homeItems.add(HomeModel(6, 0, "패스트 캠퍼스"))
    }

}