package com.app.cookify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cookify.RecipeActivity
import com.app.cookify.databinding.CategoryItemBinding
import com.app.cookify.models.Recipe
import com.bumptech.glide.Glide

class CategoryRecipeAdapter(
    private var recipeList: ArrayList<Recipe>,
    private var context: Context
) :
    RecyclerView.Adapter<CategoryRecipeAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(recipeList[position].img).into(holder.binding.imgRecipeItem)

        holder.binding.tvRecipeName.text = recipeList[position].tittle

        val time =
            recipeList[position].ing.split("\n".toRegex()).dropWhile { it.isEmpty() }.toTypedArray()

        holder.binding.tvRecipeTime.text = "\uD83D\uDD51 ${time[0]}"

        holder.binding.imgViewRecipe.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("IMAGE", recipeList[position].img)
            intent.putExtra("TITLE", recipeList[position].tittle)
            intent.putExtra("TIME", "\uD83D\uDD51 ${time[0]}")
            intent.putExtra("INGREDIENTS", recipeList[position].ing)
            intent.putExtra("STEPS", recipeList[position].des)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}