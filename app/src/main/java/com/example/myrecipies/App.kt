package com.example.myrecipies

import android.app.Application
import com.example.myrecipies.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    dataSourceModule,
                    repositoryModule,
                    recipeModules,
                    useCasesModule,
                    viewModelModule,
                    fragmentModule
                )
            )
        }
    }
}