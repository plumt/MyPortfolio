package com.test.myportfolio.di

import com.test.myportfolio.MainViewModel
import com.test.myportfolio.fastcampus.FastCampusViewModel
import com.test.myportfolio.ui.main.address.AddressViewModel
import com.test.myportfolio.ui.main.address.DongViewModel
import com.test.myportfolio.ui.main.address.RoadViewModel
import com.test.myportfolio.ui.main.bluetooth.BluetoothViewModel
import com.test.myportfolio.ui.main.calendar.CalendarViewModel
import com.test.myportfolio.ui.main.encyclopedia.EncyclopediaViewModel
import com.test.myportfolio.ui.main.firebase.FirebaseViewModel
import com.test.myportfolio.ui.main.home.HomeViewModel
import com.test.myportfolio.ui.main.movie.MovieViewModel
import com.test.myportfolio.ui.main.papago.PapagoHistoryViewModel
import com.test.myportfolio.ui.main.papago.PapagoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        MovieViewModel(get(), get(named("Naver")), get())
    }

    viewModel {
        EncyclopediaViewModel(get(), get(named("Naver")), get())
    }

    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        PapagoViewModel(get(), get(named("Naver")), get())
    }

    viewModel {
        PapagoHistoryViewModel(get(), get())
    }

    viewModel {
        AddressViewModel(get(), get())
    }

    viewModel {
        RoadViewModel(get(), get(named("Open")), get())
    }

    viewModel {
        DongViewModel(get(), get(named("Open")), get())
    }

    viewModel{
        CalendarViewModel(get(),get())
    }

    viewModel {
        BluetoothViewModel(get(), get(), get())
    }

    viewModel{
        FastCampusViewModel(get())
    }

    viewModel{
        FirebaseViewModel(get(), get())
    }
}