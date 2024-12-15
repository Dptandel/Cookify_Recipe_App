package com.app.cookify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cookify.databinding.SearchItemBinding
import com.app.cookify.models.Recipe
import com.bumptech.glide.Glide

class SearchRecipeAdapter(
    private var recipeList: ArrayList<Recipe>,
    private var context: Context
) : RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(recipeList[position].img).into(holder.binding.imgSearchItem)

        holder.binding.tvRecipeName.text = recipeList[position].tittle
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>) {
        recipeList = filterList
        notifyDataSetChanged()
    }
}