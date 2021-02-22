package com.example.myrecipies.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Recipe
import com.example.myrecipies.R
import com.example.myrecipies.presentation.RecipeListAction
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeListAdapter(var recipes: ArrayList<Recipe>, val actions: RecipeListAction) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_recipe, parent, false
        )
    )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
        holder.itemView.view_photo_display.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount() = recipes.size

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val layout = view.recipeLayout
        private val recipeTitle = view.title
        private val recipeDescription = view.description
        private val photosView = view.view_photo_display
        fun bind(recipe: Recipe) {
            recipeTitle.text = recipe.title
            recipeDescription.text = recipe.description
            val photosAdapter =
                PhotosAdapter(arrayListOf(), isDeleteVisible = false, actions = null)
            photosAdapter.updatePhotos(recipe.photosUrlList)
            photosView.adapter = photosAdapter
            layout.setOnClickListener { actions.onClick(recipe.id) }
        }
    }
}