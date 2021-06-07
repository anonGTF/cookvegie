package com.galih.cookvegie.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: RecipeEntity): Long

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}