package com.test.myportfolio.ui.main.calendar

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.myportfolio.base.BaseViewModel
import com.test.myportfolio.base.ListLiveData
import com.test.myportfolio.custom.CalendarUtils.Companion.getFormatString
import com.test.myportfolio.data.model.CalendarModel
import com.test.myportfolio.data.model.CalendarModels
import com.test.myportfolio.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.joda.time.DateTime

class CalendarViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val nowDate: Long = DateTime().withTimeAtStartOfDay().millis

    private val _selectDate = MutableLiveData<String>().apply {
        value = ""
    }
    val selectDate: LiveData<String> = _selectDate

    private val _calTitle = MutableLiveData<String>().apply {
        value = ""
    }
    val calTitle: LiveData<String> = _calTitle

    val calendarItems = ListLiveData<CalendarModels>()
    val calendarDB = ListLiveData<CalendarModels>()

    val addEventText = MutableLiveData<String>().apply {
        value = ""
    }

    var finish = MutableLiveData<Boolean>().apply {
        value = false
    }

    var choice = 0

    var index = 1

    init {
        getSelectMonth(DateTime(nowDate).getFormatString("yyyy-MM-dd"))
    }

    fun setInsertEvent() {
//        Observable.just(db.calendarDao())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .map {
//                it.insertEvent(
//                    CalendarModel(
//                        date = DateTime(selectDate.value).getFormatString("yyyyMMdd").toLong(),
//                        event = addEventText.value!!
//                    )
//                )
//            }.observeOn(AndroidSchedulers.mainThread()).subscribe({
//                getSelectMonth(selectDate.value!!)
//                getSelectEvent()
//            }, {
//                it.printStackTrace()
//            })
    }

    private fun getSelectEvent() {
//        Observable.just(db.calendarDao())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .map {
//                it.selectEvent(DateTime(selectDate.value).getFormatString("yyyyMMdd").toLong())
//            }.observeOn(AndroidSchedulers.mainThread()).subscribe({
//                calendarItems.clear(true)
//                calendarItems.add(CalendarModels(0, 3, "", ""))
//                index = 1
//                it.forEach { cal ->
//                    calendarItems.add(
//                        CalendarModels(
//                            index++,
//                            0,
//                            dateConvert(cal.date.toString()),
//                            cal.event
//                        )
//                    )
//                }
//            }, {
//                it.printStackTrace()
//            })
    }

    fun getSelectMonth(date: String) {
//        Observable.just(db.calendarDao())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .map {
//                it.selectMonth(
//                    DateTime(date).getFormatString("yyyyMM01").toLong(),
//                    DateTime(date).getFormatString("yyyyMM31").toLong()
//                )
//            }.observeOn(AndroidSchedulers.mainThread()).subscribe({
//                calendarDB.clear(true)
//                it.forEachIndexed { index, calendarModel ->
//                    calendarDB.add(
//                        CalendarModels(
//                            index,
//                            0,
//                            dateConvert(calendarModel.date.toString()),
//                            calendarModel.event
//                        )
//                    )
//                }
//            }, {
//                it.printStackTrace()
//            }, {
//                finish.value = true
//            })
    }

    fun setDeleteEvent(date: String, event: String){
//        Observable.just(db.calendarDao())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .map {
//                it.deleteEvent(DateTime(date).getFormatString("yyyyMMdd").toLong(), event)
//            }.subscribeOn(AndroidSchedulers.mainThread()).subscribe({
//                getSelectMonth(selectDate.value!!)
//                getSelectEvent()
//            },{
//                it.printStackTrace()
//            })
    }

    fun setCalDate(date: String) {
        _selectDate.value = date
        getSelectEvent()
    }

    fun setCalTitle(title: String) {
        _calTitle.value = title
    }

    private fun dateConvert(date: String): String {
        return "${date.substring(0, 4)}-${date.substring(4, 6)}-${date.substring(6, 8)}"
    }
}