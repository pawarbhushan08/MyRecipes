package com.example.myrecipies.framework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.data.Recipe
import com.example.myrecipies.framework.db.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeViewModel(val useCases: UseCases) : ViewModel() {

    private val coroutineScope = CoroutineScope((Dispatchers.IO))

    val saved = MutableLiveData<Boolean>()

    val currentRecipe = MutableLiveData<Recipe?>()

    fun saveRecipe(recipe: Recipe) {
        coroutineScope.launch {
            useCases.addRecipe(recipe)
            saved.postValue(true)
        }
    }

    fun getRecipe(id: Long) {
        coroutineScope.launch {
            currentRecipe.postValue(useCases.getRecipe(id))
        }
    }
}