package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeJson = intent.getStringExtra("recipe")
        val recipe = Gson().fromJson(recipeJson, Recipe::class.java)

        findViewById<TextView>(R.id.backButton).setOnClickListener {
        finish()
        }
        findViewById<TextView>(R.id.recipeTitle).text = recipe.title
        findViewById<TextView>(R.id.recipeIngredients).text = "Ingredients:\n" + recipe.ingredients.joinToString(", ")
        findViewById<TextView>(R.id.recipeSteps).text = "Steps:\n" + recipe.steps.joinToString("\n")
    }
}