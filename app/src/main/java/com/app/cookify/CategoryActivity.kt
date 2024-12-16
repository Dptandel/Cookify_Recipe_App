package com.app.cookify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.app.cookify.adapters.CategoryRecipeAdapter
import com.app.cookify.adapters.PopularRecipeAdapter
import com.app.cookify.databases.AppDatabase
import com.app.cookify.databinding.ActivityCategoryBinding
import com.app.cookify.models.Recipe

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryRecipeAdapter
    private lateinit var recipeList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCategoryRecyclerView()

        binding.tvCategoryName.text = intent.getStringExtra("TITLE")

        binding.imgBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun setUpCategoryRecyclerView() {
        recipeList = ArrayList()
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val db =
            Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "recipes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build()
        val daoObject = db.getDao()
        val recipes = daoObject.getAllRecipes()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                recipeList.add(recipes[i]!!)
            }
            categoryAdapter = CategoryRecipeAdapter(recipeList, this)
            binding.recyclerViewCategory.adapter = categoryAdapter
        }
    }
}