package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputContainer: LinearLayout
    private lateinit var addIngredientButton: Button
    private lateinit var searchButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addIngredientButton = findViewById(R.id.addIngredientButton)
        searchButton = findViewById(R.id.searchButton)

        inputContainer = findViewById(R.id.inputContainer)

        addIngredientButton.setOnClickListener {
            addNewIngredientField()
        }

        val removeIngredientButton = findViewById<Button>(R.id.removeIngredientButton)

        removeIngredientButton.setOnClickListener {
            removeLastIngredientField()
        }



        searchButton.setOnClickListener {
            val ingredients = getAllIngredients()
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("ingredients", ingredients.joinToString(","))
            startActivity(intent)
        }
    }


    private fun removeLastIngredientField() {
        val totalChildren = inputContainer.childCount
        var lastEditTextIndex = -1

        // Find last EditText index before the buttons
        for (i in totalChildren - 1 downTo 0) {
            val view = inputContainer.getChildAt(i)
            if (view is EditText) {
                lastEditTextIndex = i
                break
            }
        }

        // Only remove if there's more than one input
        val editTextCount = (0 until totalChildren).count { inputContainer.getChildAt(it) is EditText }
        if (editTextCount > 1 && lastEditTextIndex != -1) {
            inputContainer.removeViewAt(lastEditTextIndex)
        }
    }
    private fun addNewIngredientField() {
        val newEditText = EditText(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            hint = "Enter ingredient"
            setTextColor(resources.getColor(R.color.white))
            setHintTextColor(resources.getColor(R.color.white))
        }

        // Find the first button's index
        val insertIndex = (0 until inputContainer.childCount).indexOfFirst {
            it -> inputContainer.getChildAt(it) is LinearLayout
        }

        // Add the new EditText before the first button
        if (insertIndex != -1) {
            inputContainer.addView(newEditText, insertIndex)
        } else {
            inputContainer.addView(newEditText)
        }
    }

    private fun getAllIngredients(): List<String> {
        val ingredients = mutableListOf<String>()
        for (i in 0 until inputContainer.childCount) {
            val view = inputContainer.getChildAt(i)
            if (view is EditText) {
                val text = view.text.toString().trim()
                if (text.isNotEmpty()) ingredients.add(text)
            }
        }
        return ingredients
    }
}
