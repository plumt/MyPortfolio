package com.test.myportfolio.data.repository.api

import com.test.myportfolio.data.model.EncyclopediaModel
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.data.model.PapagoModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface NaverApi {

    @GET("/v1/search/movie.json")
    fun movie(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_service: String,
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int = 20,
    ): Observable<MovieModel.RS>

    @GET("v1/search/encyc.json")
    fun encyclopedia(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_service: String,
        @Query("query") query: String,
        @Query("start") start: Int
    ): Observable<EncyclopediaModel.RS>

    @FormUrlEncoded
    @POST("/api/user/login")
    fun papago(
        @Header("X-Naver-Client-Id") client_id: String,
        @Header("X-Naver-Client-Secret") client_service: String,
        @Field("source") source : String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Observable<PapagoModel.RS>
}