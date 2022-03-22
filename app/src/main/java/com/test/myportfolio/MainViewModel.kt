package com.test.myportfolio

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.data.model.BleMqttModel
import com.test.myportfolio.util.PreferenceManager

class MainViewModel(
    application: Application,
    val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    var finish: Boolean = false

    val mqttData = MutableLiveData<BleMqttModel>()

    val newData = MutableLiveData<Boolean>().apply {
        value = false
    }

}