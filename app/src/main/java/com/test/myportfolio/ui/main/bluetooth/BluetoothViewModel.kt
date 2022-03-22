package com.test.myportfolio.ui.main.bluetooth

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.BluetoothModel
import com.test.myportfolio.util.PreferenceManager
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanFilter
import com.test.myportfolio.data.DB
import com.test.myportfolio.data.model.TestDBModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class BluetoothViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager,
    private val database: DB
) : BaseViewModel(application) {

    val bluetoothItems = ListLiveData<BluetoothModel>()

    val btnTitle = MutableLiveData<String>().apply {
        value = "SCAN"
    }

    // 위도
    var latitude: String = ""

    // 경도
    var longitude: String = ""

    var scanDisposable: Disposable? = null
    lateinit var rxBleClient: RxBleClient

    val scanFilter: ArrayList<ScanFilter> = arrayListOf()
    val address: ArrayList<String> = arrayListOf()

    var uuid = ""

    var index = 0

    init {
        address.apply {
            add("CB:9D:96:E6:8E:BA")
            add("F6:D7:A0:F5:8E:97")
            add("04:32:F4:67:8C:6A")
            add("C6:E9:EE:7B:81:D4")
            add("DB:29:DF:0F:1E:02")
            add("D4:77:6E:71:75:AD")
        }
        address.forEach {
            scanFilter.add(
                ScanFilter.Builder()
                    .setDeviceAddress(it)
                    .build()
            )
        }






        Observable.just(database.testDao()).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.insertEvent(TestDBModel(
                    mac = "aaa"
                ))
            }.observeOn(AndroidSchedulers.mainThread()).subscribe({
                Log.d("lys","성공11")
                dataBase()
            },{
                Log.e("lys","error111 : ${it.message}")
            })

    }

    fun dataBase(){
        Observable.just(database.testDao()).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.selectEvent().forEach {
                    Log.d("lys","test -> ${it.mac}")
                }

            }.subscribe({
                Log.d("lys","성공22")
            },{
                Log.e("lys","error222 : ${it.message}")
            })
    }
}