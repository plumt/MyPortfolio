package com.test.myportfolio.ui.main.papago

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.R
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.data.repository.api.NaverApi
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PapagoViewModel(
    application: Application,
    private val api: NaverApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val inputText = MutableLiveData<String>()
    val transText = MutableLiveData<String>()

    private fun getPapago() {
        api.papago(
            mContext.getString(R.string.CLIENT_ID),
            mContext.getString(R.string.CLIENT_SERVICE),
            "ko",
            "en",
            inputText.value!!
        ).observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.message != null) {
                    Observable.just(it)
                } else {
                    Observable.empty()
                }
            }.observeOn(AndroidSchedulers.mainThread()).map {
                transText.value = it.message.result.translatedText
                if (sharedPreferences.getString(mContext, inputText.value) == "") {
                    sharedPreferences.setString(
                        mContext,
                        inputText.value,
                        it.message.result.translatedText
                    )
                }

            }.subscribe({

            }, {
                it.printStackTrace()
            })
    }

    fun btnClick(type: String) {
        when (type) {
            "번역" -> {
                if (inputText.value!!.trim() != "") {
                    getPapago()
                }
            }
            "기록" -> {
                navigatorFlag.value = R.id.action_papagoFragment_to_papagoHistoryFragment
            }
            "초기화" -> {
                inputText.value = ""
                transText.value = ""
            }
            "복사" -> {
                if (transText.value != "") {
                    (mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
                        setPrimaryClip(ClipData.newPlainText("label", transText.value))
                    }
                    Toast.makeText(mContext, "복사되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onTextChanged(s: CharSequence) {
        if (transText.value != "") {
            transText.value = ""
        }
    }
}