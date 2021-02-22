package com.example.myrecipies.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = REPLACE)//If recipe is already present then just replace it
    suspend fun addRecipeEntity(recipeEntity: RecipeEntity)

    @Query(value = "SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeEntity(id: Long): RecipeEntity?

    @Query(value = "SELECT * FROM recipe")
    suspend fun getAllRecipeEntity(): List<RecipeEntity>
}