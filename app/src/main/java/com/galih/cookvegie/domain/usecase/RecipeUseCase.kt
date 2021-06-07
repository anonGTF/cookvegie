package com.galih.cookvegie.domain.usecase

import com.galih.cookvegie.data.source.remote.RecipeResponse
import com.galih.cookvegie.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call

interface RecipeUseCase {

    suspend fun uploadImage(image: MultipartBody.Part): Call<RecipeResponse>
    suspend fun upsert(recipe: Recipe): Long
    fun getAllFavRecipes(): Flow<List<Recipe>>
    suspend fun deleteFavRecipe(recipe: Recipe)

}