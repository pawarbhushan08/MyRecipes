package com.example.myrecipies.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.core.data.Recipe

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String,
    @TypeConverters(RoomTypeConverters::class)
    val photosList: List<String>? = listOf()
) {
    companion object {
        fun fromRecipe(recipe: Recipe) = RecipeEntity(
            id = recipe.id,
            title = recipe.title,
            description = recipe.description,
            photosList = recipe.photosUrlList
        )
    }

    fun toRecipe() =
        Recipe(id = id, title = title, description = description, photosUrlList = photosList)
}