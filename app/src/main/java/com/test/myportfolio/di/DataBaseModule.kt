package com.test.myportfolio.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.myportfolio.data.DB
import org.koin.dsl.module

val databaseModule = module {


    var INSTANCE: DB? = null
    val NAME = DB::class.java.simpleName


    fun getInstance(context: Context): DB? {
        if (INSTANCE == null) {
            synchronized(DB::class) {
                INSTANCE =
                    Room.databaseBuilder(context.applicationContext, DB::class.java, "$NAME.db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
        }
        return INSTANCE!!
    }

    single {
        getInstance(get())
    }

}