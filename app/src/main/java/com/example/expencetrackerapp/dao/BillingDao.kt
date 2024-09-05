package com.example.expencetrackerapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expencetrackerapp.models.Billing

@Dao
interface BillingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Billing)

    @Update
    fun update(note: Billing)


    @Update
    fun updateRead(note: Billing)


    @Query("SELECT * FROM billing ORDER BY title ASC")
    fun getAllNotes(): LiveData<List<Billing>>

}
