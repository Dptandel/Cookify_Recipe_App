package com.app.cookify.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.cookify.daos.RecipeDao
import com.app.cookify.models.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): RecipeDao
}