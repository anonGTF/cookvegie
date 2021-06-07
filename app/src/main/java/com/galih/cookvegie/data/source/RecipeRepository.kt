package com.galih.cookvegie.data.source

import androidx.lifecycle.LiveData
import com.galih.cookvegie.data.source.local.RecipeDatabase
import com.galih.cookvegie.data.source.remote.RecipeApi
import com.galih.cookvegie.data.source.remote.RecipeResponse
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.domain.repository.IRecipeRepository
import com.galih.cookvegie.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import retrofit2.Call
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: RecipeApi,
    private val db: RecipeDatabase
): IRecipeRepository {
    override suspend fun uploadImage(image: MultipartBody.Part): Call<RecipeResponse>
            = api.uploadImage(image)

    override suspend fun upsert(recipe: Recipe): Long =
        db.getRecipeDao().upsert(DataMapper.mapModelToEntity(recipe))

    override fun getAllFavRecipes(): Flow<List<Recipe>> =
        db.getRecipeDao().getAllRecipes().map {
            DataMapper.mapEntitiesToModel(it)
        }

    override suspend fun deleteFavRecipe(recipe: Recipe) =
        db.getRecipeDao().deleteRecipe(DataMapper.mapModelToEntity(recipe))
}