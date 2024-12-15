package com.app.cookify.daos

import androidx.room.Dao
import androidx.room.Query
import com.app.cookify.models.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): List<Recipe?>?

}