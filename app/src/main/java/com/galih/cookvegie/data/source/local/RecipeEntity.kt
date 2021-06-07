package com.galih.cookvegie.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe"
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val judul: String,
    val bahan: List<String>,
    val kuantitas_bahan: List<String>,
    val thumbnail: String,
    val kalori: String,
    val prosedur: List<String>,
)