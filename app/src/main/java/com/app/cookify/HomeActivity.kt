package com.app.cookify

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.app.cookify.adapters.PopularRecipeAdapter
import com.app.cookify.databases.AppDatabase
import com.app.cookify.databinding.ActivityHomeBinding
import com.app.cookify.databinding.BottomSheetBinding
import com.app.cookify.models.Recipe
import com.google.android.material.bottomsheet.BottomSheetDialog

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

        binding.salad.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Salad")
            intent.putExtra("CATEGORY", "Salad")
            startActivity(intent)
        }
        binding.mainDish.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Main Dishes")
            intent.putExtra("CATEGORY", "Dish")
            startActivity(intent)
        }
        binding.drinks.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Drinks")
            intent.putExtra("CATEGORY", "Drinks")
            startActivity(intent)
        }
        binding.dessert.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TITLE", "Desserts")
            intent.putExtra("CATEGORY", "Desserts")
            startActivity(intent)
        }

        binding.imgInfo.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val dialogBinding = BottomSheetBinding.inflate(layoutInflater)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogBinding.root)

            dialog.window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(android.view.Gravity.BOTTOM)
            }

            dialogBinding.linearPrivacyPolicy.setOnClickListener {
                Toast.makeText(this, "Copyright @2024 by DCreations Studio", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }

            dialogBinding.linearAboutDeveloper.setOnClickListener {
                Toast.makeText(this, "Developer: DHARMIN TANDEL", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun setUpPopularRecyclerView() {
        recipeList = ArrayList()
        binding.recyclerViewPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "recipes")
            .allowMainThreadQueries().fallbackToDestructiveMigration().createFromAsset("recipe.db")
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