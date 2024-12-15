package com.app.cookify

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.app.cookify.adapters.PopularRecipeAdapter
import com.app.cookify.adapters.SearchRecipeAdapter
import com.app.cookify.databases.AppDatabase
import com.app.cookify.databinding.ActivitySearchBinding
import com.app.cookify.models.Recipe
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchRecipeAdapter
    private lateinit var recipeList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.requestFocus()

        val db =
            Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "recipes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build()
        val daoObject = db.getDao()
        recipes = daoObject.getAllRecipes()!!

        setUpSearchRecyclerView()

        binding.imgBackBtn.setOnClickListener {
            finish()
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // before text changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    filterList(s.toString())
                } else {
                    setUpSearchRecyclerView()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // after text changed
            }

        })
    }

    private fun filterList(filterText: String) {
        val filteredList = ArrayList<Recipe>()
        for (i in recipes.indices) {
            if (recipes[i]!!.tittle.toLowerCase(Locale.ROOT)
                    .contains(filterText.toLowerCase(Locale.ROOT))
            ) {
                filteredList.add(recipes[i]!!)
            }
            searchAdapter.filterList(filteredList)
        }
    }

    private fun setUpSearchRecyclerView() {
        recipeList = ArrayList()
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        for (i in recipes.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                recipeList.add(recipes[i]!!)
            }
            searchAdapter = SearchRecipeAdapter(recipeList, this)
            binding.recyclerViewSearch.adapter = searchAdapter
        }
    }
}