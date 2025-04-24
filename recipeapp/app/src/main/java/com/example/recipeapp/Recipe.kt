package com.example.recipeapp

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<String>,
    val steps: List<String>
)