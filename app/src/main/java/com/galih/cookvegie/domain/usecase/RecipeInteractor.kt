package com.galih.cookvegie.domain.usecase

import com.galih.cookvegie.data.source.remote.RecipeResponse
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.domain.repository.IRecipeRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val repository: IRecipeRepository
): RecipeUseCase {
    override suspend fun uploadImage(image: MultipartBody.Part): Call<RecipeResponse> =
        repository.uploadImage(image)

    override suspend fun upsert(recipe: Recipe): Long = repository.upsert(recipe)

    override fun getAllFavRecipes(): Flow<List<Recipe>> = repository.getAllFavRecipes()

    override suspend fun deleteFavRecipe(recipe: Recipe) = repository.deleteFavRecipe(recipe)
}