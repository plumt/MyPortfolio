package com.test.myportfolio.ui.main.movie

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

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

    fun getMovieList() {
        api.movie(
            mContext.getString(R.string.CLIENT_ID),
            mContext.getString(R.string.CLIENT_SERVICE),
            searchMovie.value!!.trim(),
            page
        )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.items != null) {
                    Observable.just(it)
                } else {
                    Observable.empty()
                }

            }.observeOn(AndroidSchedulers.mainThread()).map {
                it.items!!.forEach { movieList ->
                    var equals = true
                    movieItems.value!!.forEach {
                        if (it.link == movieList.link) {
                            equals = false
                        }
                    }
                    if (equals) {
                        movieItems.add(
                            MovieModel.RS.List(
                                index++,
                                0,
                                movieList.title,
                                movieList.link,
                                movieList.image,
                                movieList.pubDate,
                                movieList.director,
                                movieList.actor,
                                movieList.userRating
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
        reset()
        getMovieList()
    }

    private fun reset() {
        page = 1
        flag = true
        movieItems.clear(true)
    }
}