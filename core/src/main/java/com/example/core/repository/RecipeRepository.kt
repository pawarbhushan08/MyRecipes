package com.example.core.repository

import com.example.core.data.Recipe

class RecipeRepository(val dataSource: RecipeDataSource) {

    suspend fun addRecipe(recipe: Recipe) = dataSource.add(recipe)

    suspend fun getRecipe(id: Long) = dataSource.get(id)

    suspend fun getAllRecipe() = dataSource.getAll()
}