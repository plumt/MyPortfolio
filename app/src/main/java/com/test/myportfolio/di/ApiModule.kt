package com.test.myportfolio.di

import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.data.repository.api.OpenApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun providerNaverApi(retrofit: Retrofit): NaverApi {
        return retrofit.create(NaverApi::class.java)
    }
    fun providerOpenApi(retrofit: Retrofit): OpenApi {
        return retrofit.create(OpenApi::class.java)
    }
//    single{providerNaverApi(get())}
    single(named("Naver")) { providerNaverApi(get(named("naver"))) }
    single(named("Open")) { providerOpenApi(get(named("open"))) }
}