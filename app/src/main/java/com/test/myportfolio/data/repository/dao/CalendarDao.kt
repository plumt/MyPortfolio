package com.test.myportfolio.data.repository.dao

import androidx.room.*
import com.test.myportfolio.data.model.CalendarModel

@Dao
abstract class CalendarDao {

    @Query("SELECT * FROM CALENDAR WHERE DATE == :Dt")
    abstract fun selectEvent(Dt: Long): List<CalendarModel>

    @Query("SELECT * FROM CALENDAR WHERE DATE >= :sDt AND DATE <= :eDt ORDER BY DATE")
    abstract fun selectMonth(sDt: Long, eDt: Long): List<CalendarModel>

    @Query("DELETE FROM Calendar WHERE DATE == :dt AND EVENT == :event")
    abstract fun deleteEvent(dt: Long, event: String)

    @Insert
    abstract fun insertEvent(calendarModel: CalendarModel): Long
}