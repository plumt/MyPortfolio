package com.test.myportfolio

import android.app.Application
import com.test.myportfolio.data.DB
import com.test.myportfolio.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PortfolioApplication : Application() {

    var id = 0
//    lateinit var db: DB
    override fun onCreate() {
        super.onCreate()
//        db = DB.getInstance(this)!!
        startKoin {
            androidLogger()
            androidContext(this@PortfolioApplication)
            koin.loadModules(
                listOf(
                    viewModelModule,
                    sharedPreferences,
                    networkModule,
                    apiModule,
                    databaseModule
                )
            )
            koin.createRootScope()
        }
    }
}