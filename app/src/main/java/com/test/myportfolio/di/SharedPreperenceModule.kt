package com.test.myportfolio.di

import com.test.myportfolio.util.PreferenceManager
import org.koin.dsl.module

val sharedPreferences = module {
    fun provideSharedPref(): PreferenceManager {
        return PreferenceManager
    }
    single { provideSharedPref() }
}