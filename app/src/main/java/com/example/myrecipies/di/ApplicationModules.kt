package com.example.myrecipies.di

import com.example.core.repository.RecipeDataSource
import com.example.core.repository.RecipeRepository
import com.example.core.usecase.AddRecipe
import com.example.core.usecase.GetAllRecipe
import com.example.core.usecase.GetRecipe
import com.example.myrecipies.framework.AddRecipeViewModel
import com.example.myrecipies.framework.RecipeListViewModel
import com.example.myrecipies.framework.RoomRecipeDataSource
import com.example.myrecipies.framework.db.UseCases
import com.example.myrecipies.presentation.AddRecipeFragment
import com.example.myrecipies.presentation.RecipeListFragment
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    factory<RecipeDataSource> { RoomRecipeDataSource(androidContext()) }
}

val repositoryModule = module {
    factory { RecipeRepository(get()) }
}

val recipeModules = module {
    factory { AddRecipe(get()) }
    factory { GetAllRecipe(get()) }
    factory { GetRecipe(get()) }

}

val useCasesModule = module {
    factory { UseCases(get(), get(), get()) }
}

val fragmentModule = module {
    factory { AddRecipeFragment() }
    factory { RecipeListFragment() }
}

val viewModelModule = module {
    factory { AddRecipeViewModel(get()) }
    factory { RecipeListViewModel(get()) }
}

