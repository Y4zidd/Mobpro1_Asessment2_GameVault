package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.Constants

@Database(entities = [Game::class], version = 2, exportSchema = false)
abstract class GameDb : RoomDatabase() {
    abstract val dao: GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDb? = null

        fun getInstance(context: Context): GameDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDb::class.java,
                        Constants.DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
