package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Category
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [Game::class, Category::class], version = 3, exportSchema = false)
abstract class GameDb : RoomDatabase() {
    abstract val dao: GameDao
    abstract val categoryDao: CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: GameDb? = null

        fun getInstance(context: Context): GameDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDb::class.java,
                    Constants.DATABASE_NAME
                )
                .addCallback(object : Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val catDao = getInstance(context).categoryDao
                            val currentCats = catDao.getAllCategories().first()
                            if (currentCats.isEmpty()) {
                                catDao.insertCategories(listOf(
                                    Category(name = "RPG"),
                                    Category(name = "Action"),
                                    Category(name = "Adventure"),
                                    Category(name = "Simulation"),
                                    Category(name = "Sports"),
                                    Category(name = "Other")
                                ))
                            }
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
