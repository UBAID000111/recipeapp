package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListActivity : AppCompatActivity() {

    private lateinit var recipeListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recipeListView = findViewById(R.id.recipeListView)

        findViewById<TextView>(R.id.backButton2).setOnClickListener {
            finish()
        }

        // Get ingredients from intent and split into list
        val ingredients = intent.getStringExtra("ingredients")?.split(",")?.map { it.trim().lowercase() } ?: listOf()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Load JSON from assets folder
                val json = assets.open("recipe_data_100.json")
                    .bufferedReader()
                    .use { it.readText() }

                // Convert JSON to List<Recipe>
                val recipeListType = object : TypeToken<List<Recipe>>() {}.type
                val recipes: List<Recipe> = Gson().fromJson(json, recipeListType)

                // Filter recipes based on input ingredients
                val matchedRecipes = recipes.filter { recipe ->
                    ingredients.all { userIng ->
                        recipe.ingredients.any{ recipeIng ->
                            recipeIng.lowercase().contains(userIng)
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    val adapter = RecipeAdapter(this@RecipeListActivity, matchedRecipes)
                    recipeListView.adapter = adapter

                    // Navigate to detail screen on click
                    recipeListView.setOnItemClickListener { _, _, position, _ ->
                        val intent = Intent(this@RecipeListActivity, RecipeDetailActivity::class.java)
                        intent.putExtra("recipe", Gson().toJson(matchedRecipes[position]))
                        startActivity(intent)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}