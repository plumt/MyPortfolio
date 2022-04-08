package com.test.myportfolio.data.repository.api

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.test.myportfolio.R
import com.test.myportfolio.base.Item
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.data.model.RootModel
import com.test.myportfolio.ui.main.movie.MovieViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

//class ApiClass(var result: Any? = null) {
//
//    fun <T : Observable<*>> callApi(api: T) : Observable<*> {
//        return api.observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
//            .flatMap { Observable.just(it) }
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                result = it
//            }
//    }
//
//    fun <T : Item> recyclerViewIdSetting(model: ArrayList<T>, plus: Int = 0) : ArrayList<T>{
//        model.forEachIndexed { index, t ->
//            model[index].id = index + plus
//            model[index].viewType = 0
//        }
//        return model
//    }
//
//}

//class ApiClass <T: Observable<*>>(val api: T){
//
//    fun callApi () : Observable<*> {
//        return api.observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
//            .flatMap { Observable.just(it) }
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                it
//            }
//    }
//
//}

class ApiClass<T : Response<*>>(val api: T) {
    suspend fun callApi(): Any? {
        var result: Any? = null
        CoroutineScope(Dispatchers.IO).async {
            if (api.isSuccessful) {
                result = api.body()!!
            }
        }.join()
        return result
    }

    fun <T : Item> rvIdSetting(model: ArrayList<T>, plus: Int = 0): ArrayList<T> {
        model.forEachIndexed { index, t ->
            model[index].id = index + plus
            model[index].viewType = 0
        }
        return model
    }
}