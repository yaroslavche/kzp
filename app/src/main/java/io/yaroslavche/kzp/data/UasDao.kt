package io.yaroslavche.kzp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UasDao {
    @Query("SELECT * FROM uas")
    fun getAll(): LiveData<List<Uas>>

    @Insert
    suspend fun insert(uas: Uas)
}
