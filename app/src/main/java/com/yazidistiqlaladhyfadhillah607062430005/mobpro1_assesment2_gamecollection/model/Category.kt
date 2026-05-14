package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val name: String
)
