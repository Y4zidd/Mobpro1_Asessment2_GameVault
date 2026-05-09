package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val platform: String,
    val rating: Int,
    val playTime: Int = 0, // Waktu bermain dalam jam
    val finished: Boolean,
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
