package com.galih.cookvegie.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.domain.usecase.RecipeUseCase
import com.galih.cookvegie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
): ViewModel() {

    fun getAllFavRecipes() = recipeUseCase.getAllFavRecipes().asLiveData()

    fun deleteFav(recipe: Recipe) = viewModelScope.launch {
        recipeUseCase.deleteFavRecipe(recipe)
    }

    fun saveFav(recipe: Recipe) = viewModelScope.launch {
        recipeUseCase.upsert(recipe)
    }
}