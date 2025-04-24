package com.example.recipeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RecipeAdapter(context: Context, recipes: List<Recipe>)
    : ArrayAdapter<Recipe>(context, 0, recipes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val recipe = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = recipe?.title
        return view
    }
}