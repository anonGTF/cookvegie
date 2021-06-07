package com.galih.cookvegie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
): ViewModel() {

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeUseCase.upsert(recipe)
    }

}