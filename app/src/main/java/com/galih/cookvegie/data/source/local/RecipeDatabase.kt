package com.galih.cookvegie.data.source.local

import android.content.Context
import androidx.room.*

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var instance: RecipeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RecipeDatabase::class.java,
                "recipe_db.db"
            ).build()
    }
}