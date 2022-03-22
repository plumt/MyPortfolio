package com.test.myportfolio.data.repository.api

import com.test.myportfolio.data.model.AddressModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApi {

    @GET("/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd")
    fun address(
        @Query("ServiceKey") ServiceKey: String,
        @Query("searchSe") searchSe: String,
        @Query("srchwrd") srchwrd: String
    ): Observable<AddressModel.RS>

}