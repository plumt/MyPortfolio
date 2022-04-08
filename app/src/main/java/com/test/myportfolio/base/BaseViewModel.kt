package com.test.myportfolio.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.myportfolio.PortfolioApplication
import com.test.myportfolio.data.repository.api.ApiClass
import kotlinx.coroutines.async
import retrofit2.Response

open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    val mContext = application.applicationContext
//    val db = (application as PortfolioApplication).db

    val navigatorFlag = MutableLiveData<Int>()

    suspend fun callApi(api: Response<*>): Any? {
        var result: Any? = null
        viewModelScope.async {
            async { result = ApiClass(api).callApi() }.join()
        }.await()
        return result
    }

    fun <T : Item> setId(model: ArrayList<T>, plus: Int = 0): ArrayList<T> {
        model.forEachIndexed { index, t ->
            model[index].id = index + plus
            model[index].viewType = 0
        }
        return model
    }
}