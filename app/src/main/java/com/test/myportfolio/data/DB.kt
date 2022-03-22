package com.test.myportfolio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.myportfolio.data.model.CalendarModel
import com.test.myportfolio.data.model.TestDBModel
import com.test.myportfolio.data.model.TestModel
import com.test.myportfolio.data.repository.dao.CalendarDao
import com.test.myportfolio.data.repository.dao.TestDao

@Database(entities = [CalendarModel::class, TestDBModel::class], version = 2, exportSchema = true)
abstract class DB : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun testDao(): TestDao

}