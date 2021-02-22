package com.example.myrecipies.framework.db

import com.example.core.usecase.AddRecipe
import com.example.core.usecase.GetAllRecipe
import com.example.core.usecase.GetRecipe

data class UseCases(
    val addRecipe: AddRecipe,
    val getAllRecipe: GetAllRecipe,
    val getRecipe: GetRecipe
) {
}