package com.test.myportfolio.data.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.myportfolio.data.model.TestDBModel

@Dao
abstract class TestDao {

    @Query("SELECT * FROM Test")
    abstract fun selectEvent(): List<TestDBModel>


    @Insert
    abstract fun insertEvent(testDBModel: TestDBModel): Long
}