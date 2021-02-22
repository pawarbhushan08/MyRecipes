package com.example.myrecipies.framework

import android.content.Context
import com.example.core.data.Recipe
import com.example.core.repository.RecipeDataSource
import com.example.myrecipies.framework.db.DatabaseService
import com.example.myrecipies.framework.db.RecipeEntity

class RoomRecipeDataSource(context: Context) : RecipeDataSource {

    val recipeDao = DatabaseService.getInstance(context).recipeDao()

    override suspend fun add(recipe: Recipe) = recipeDao.addRecipeEntity(RecipeEntity.fromRecipe(recipe))

    override suspend fun get(id: Long) = recipeDao.getRecipeEntity(id)?.toRecipe()

    override suspend fun getAll() = recipeDao.getAllRecipeEntity().map { it.toRecipe() }
}