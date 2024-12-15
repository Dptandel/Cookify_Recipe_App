package com.app.cookify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.app.cookify.adapters.PopularRecipeAdapter
import com.app.cookify.databases.AppDatabase
import com.app.cookify.databinding.ActivityHomeBinding
import com.app.cookify.models.Recipe

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var popularAdapter: PopularRecipeAdapter
    private lateinit var recipeList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPopularRecyclerView()

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun setUpPopularRecyclerView() {
        recipeList = ArrayList()
        binding.recyclerViewPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val db =
            Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "recipes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build()
        val daoObject = db.getDao()
        val recipes = daoObject.getAllRecipes()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                recipeList.add(recipes[i]!!)
            }
            popularAdapter = PopularRecipeAdapter(recipeList, this)
            binding.recyclerViewPopular.adapter = popularAdapter
        }
    }
}