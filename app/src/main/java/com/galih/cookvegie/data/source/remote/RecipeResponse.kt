package com.galih.cookvegie.data.source.remote

import android.os.Parcelable
import com.galih.cookvegie.domain.model.Recipe
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeResponse(
        val labels: List<String>,
        val rawLabels: List<String>,
        val recipes: List<Recipe>
): Parcelable