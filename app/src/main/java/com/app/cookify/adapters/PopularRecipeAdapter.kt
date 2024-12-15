package com.app.cookify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cookify.databinding.PopularItemBinding
import com.app.cookify.models.Recipe
import com.bumptech.glide.Glide

class PopularRecipeAdapter(
    private var recipeList: ArrayList<Recipe>,
    private var context: Context
) :
    RecyclerView.Adapter<PopularRecipeAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(recipeList[position].img).into(holder.binding.imgPopularItem)

        holder.binding.tvPopularItemName.text = recipeList[position].tittle

        val time =
            recipeList[position].ing.split("\n".toRegex()).dropWhile { it.isEmpty() }.toTypedArray()

        holder.binding.tvPopularItemTime.text = "\uD83D\uDD51 ${time[0]}"
    }
}