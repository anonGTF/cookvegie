package com.galih.cookvegie.di

import android.content.Context
import com.galih.cookvegie.data.source.RecipeRepository
import com.galih.cookvegie.data.source.local.RecipeDatabase
import com.galih.cookvegie.data.source.remote.RecipeApi
import com.galih.cookvegie.data.source.remote.RetrofitInstance.Companion.api
import com.galih.cookvegie.domain.repository.IRecipeRepository
import com.galih.cookvegie.domain.usecase.RecipeInteractor
import com.galih.cookvegie.domain.usecase.RecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi = api

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase
            = RecipeDatabase(context)

    @Provides
    @Singleton
    fun provideRecipeRepository(api: RecipeApi, db: RecipeDatabase): IRecipeRepository =
        RecipeRepository(api, db)

    @Provides
    @Singleton
    fun provideRecipeUseCase(recipeRepository: RecipeRepository): RecipeUseCase
            = RecipeInteractor(recipeRepository)

}