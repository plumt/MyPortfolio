package com.test.myportfolio.ui.main.movie

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myportfolio.MainViewModel
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.data.repository.api.ApiClass
//import com.test.myportfolio.data.repository.api.ApiClass
import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import org.koin.android.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.ext.scope

class MovieViewModel(
    application: Application,
    private val api: NaverApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val movieItems = ListLiveData<MovieModel.RS.List>()

    val searchMovie = MutableLiveData<String>().apply {
        value = ""
    }

    private var flag = false

    private var index = 0

    private var page = 1

    fun getMovieList(clear: Boolean = false) {
        viewModelScope.launch {
            try {
                (callApi(
                    api.movie(
                        mContext.getString(R.string.CLIENT_ID),
                        mContext.getString(R.string.CLIENT_SERVICE),
                        searchMovie.value!!.trim(),
                        page
                    )
                ) as MovieModel.RS).items!!.run {
                    if (clear) reset()
                    movieItems.addAll(setId(this, movieItems.value!!.size))
                }
            } catch (e: Throwable) {
                Log.e("lys", "error : ${e.message}")
            }
        }


//        ApiClass(
//            api.movie(
//                mContext.getString(R.string.CLIENT_ID),
//                mContext.getString(R.string.CLIENT_SERVICE),
//                searchMovie.value!!.trim(),
//                page
//            )
//        ).run{
//            Log.d("lys","result : ${callApi().items}")
//        }

//            .run {
//            callApi().map {
//                Log.d("lys", "result -> ${it}")
//            }.subscribe({
//                Log.d("lys", "success : ${movieItems.value!!}")
//            }, {
//                Log.d("lys", "fail : ${it.message}")
//            })
//        }
    }

    fun nextPage() {
        if (flag) {
            flag = false
            page += 20
            getMovieList()
        }
    }

    fun textClear() {
//        reset()
        searchMovie.value = ""
    }

    fun searchBtn() {
        index = 0
        getMovieList(true)
    }

    private fun reset() {
        page = 1
        flag = true
        movieItems.clear(true)
    }
}