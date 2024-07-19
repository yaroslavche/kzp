package io.yaroslavche.kzp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Uas::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun uasDao(): UasDao
}