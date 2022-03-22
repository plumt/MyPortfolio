package com.test.myportfolio.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.PortfolioApplication

open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    val mContext = application.applicationContext
//    val db = (application as PortfolioApplication).db

    val navigatorFlag = MutableLiveData<Int>()
}