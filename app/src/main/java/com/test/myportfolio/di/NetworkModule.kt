package com.test.myportfolio.di

import com.test.myportfolio.R
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    val connectTimeout: Long = 40// 20s
    val readTimeout: Long = 40 // 20s

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String, type: Int): Retrofit {
        return if (type == 1) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()
        } else {
            Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()

        }
    }
    single { provideHttpClient() }

    single(named("naver")) {
        val baseUrl = androidContext().getString(R.string.NAVER_URL)
        provideRetrofit(get(), baseUrl, 1)
    }

    single(named("open")) {
        val baseUrl = androidContext().getString(R.string.OPEN_URL)
        provideRetrofit(get(), baseUrl, 2)
    }

//    single {
//        val baseUrl = androidContext().getString(R.string.NAVER_URL)
//        provideRetrofit(get(), baseUrl)
//    }
}