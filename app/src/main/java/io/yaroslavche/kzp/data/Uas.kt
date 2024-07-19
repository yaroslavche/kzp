package io.yaroslavche.kzp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uas")
data class Uas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)