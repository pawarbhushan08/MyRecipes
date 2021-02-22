package com.example.core.data

data class Recipe(
    var id: Long = 0,
    var title: String,
    var description: String,
    var photosUrlList: List<String>?
)