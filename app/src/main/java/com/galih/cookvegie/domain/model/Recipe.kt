package com.galih.cookvegie.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
        val id: Int,
        val judul: String,
        val bahan: List<String>,
        val kuantitas_bahan: List<String>,
        val thumbnail: String,
        val kalori: String,
        val prosedur: List<String>,
): Parcelable