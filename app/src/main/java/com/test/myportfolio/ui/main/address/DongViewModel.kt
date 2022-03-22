package com.test.myportfolio.ui.main.address

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.AddressModel
import com.test.myportfolio.data.repository.api.OpenApi
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URLDecoder

class DongViewModel(
    application: Application,
    private val api: OpenApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application){


    val dongAddress= MutableLiveData<String>()
    val addressItems = ListLiveData<AddressModel.AddressModels.List>()

    var index = 0

    fun getAddress() {
        api.address(
            URLDecoder.decode(mContext.getString(R.string.ADDRESS_KEY), "UTF-8"),
            "dong",
            dongAddress.value!!
        )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it != null) {
                    Observable.just(it)
                } else {
                    Observable.empty()
                }
            }.observeOn(AndroidSchedulers.mainThread()).map {
                addressItems.clear(true)
                it.newAddressListAreaCd.forEach {
                    addressItems.add(
                        AddressModel.AddressModels.List(
                            index++,
                            0,
                            it.zipNo,
                            it.lnmAdres,
                            it.rnAdres
                        )
                    )
                }
                Log.d("lys","address : ${it}")
            }.subscribe({
                Log.d("lys", "success : $it")
            }, {
                it.printStackTrace()
                Log.e("lys", "error : $it")
            })
    }

    fun onClick(){
        if(dongAddress.value!!.isNotEmpty()){
            index= 0
            getAddress()
        }
    }

}
