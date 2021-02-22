package com.example.core.repository

import com.example.core.data.Recipe

interface RecipeDataSource {
    suspend fun add(recipe: Recipe)

    suspend fun get(id: Long): Recipe?

    suspend fun getAll(): List<Recipe>
}