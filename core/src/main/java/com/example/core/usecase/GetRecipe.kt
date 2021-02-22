package com.example.core.usecase

import com.example.core.repository.RecipeRepository

class GetRecipe(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(id: Long) = recipeRepository.getRecipe(id)
}