package com.test.myportfolio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.myportfolio.base.Item

@Entity(tableName = "Calendar")
data class CalendarModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SEQ") val seq: Long = 0,
    @ColumnInfo(name = "DATE") val date: Long = 0,
    @ColumnInfo(name = "EVENT") val event: String = ""
)

data class CalendarModels(
    override var id: Int = 0,
    override var viewType: Int = 0,
    val date: String = "",
    val event: String = ""
) : Item()
