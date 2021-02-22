package com.example.core.usecase

import com.example.core.data.Recipe
import com.example.core.repository.RecipeRepository

class AddRecipe(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) = recipeRepository.addRecipe(recipe)
}