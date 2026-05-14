package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game WHERE isDeleted = 0 ORDER BY title ASC")
    fun getAllGames(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE isDeleted = 1 ORDER BY createdAt DESC")
    fun getDeletedGames(): Flow<List<Game>>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getGameById(id: Long): Game?

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("UPDATE game SET isDeleted = 1 WHERE id = :id")
    suspend fun moveToTrash(id: Long)

    @Query("UPDATE game SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreFromTrash(id: Long)

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun deletePermanentlyById(id: Long)

    @Delete
    suspend fun deletePermanently(game: Game)
}
