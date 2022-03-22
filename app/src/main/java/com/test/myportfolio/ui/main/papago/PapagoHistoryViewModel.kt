package com.test.myportfolio.ui.main.papago

import android.app.Application
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.PapagoHistoryModel
import com.test.myportfolio.util.PreferenceManager

class PapagoHistoryViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val historyItems = ListLiveData<PapagoHistoryModel>()

    init {
        //                val a = sharedPreferences.getAll(mContext)
//                a.forEach {
//                    Log.d(
//                        "lys",
//                        "sharedPreference : ${it} : ${
//                            sharedPreferences.getString(
//                                mContext,
//                                it.toString()
//                            )
//                        }"
//                    )
//                }
        sharedPreferences.getAll(mContext).forEachIndexed { index, key ->
            historyItems.add(
                PapagoHistoryModel(
                    index,
                    0,
                    key.toString(),
                    sharedPreferences.getString(mContext, key.toString()) ?: ""
                )
            )
        }
    }
}