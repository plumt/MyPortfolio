package com.test.myportfolio

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.data.model.BleMqttModel
import com.test.myportfolio.data.model.MovieModel
import com.test.myportfolio.data.repository.api.ApiClass
import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.util.PreferenceManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

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