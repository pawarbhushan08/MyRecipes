package com.example.myrecipies.framework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.data.Recipe
import com.example.myrecipies.framework.db.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListViewModel(val useCases: UseCases) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipes() {
        coroutineScope.launch {
            val noteList = useCases.getAllRecipe()
            recipes.postValue(noteList)
        }
    }

}