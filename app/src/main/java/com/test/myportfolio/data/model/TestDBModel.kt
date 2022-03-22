package com.test.myportfolio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
data class TestDBModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SEQ") val seq: Long = 0,
    @ColumnInfo(name = "MAC") val mac: String = ""
)

data class TestModel(
    val mac: String = ""
)
