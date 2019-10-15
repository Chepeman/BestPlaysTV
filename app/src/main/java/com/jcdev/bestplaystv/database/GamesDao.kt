package com.jcdev.bestplaystv.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcdev.bestplaystv.model.Game

@Dao
interface GamesDao {
    @Query("SELECT * from games WHERE `title` LIKE (:query)")
    fun getGamesBySearch(query: String): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game)

    @Query("SELECT * from games WHERE `id` = (:id)")
    fun getGameById(id: String): Game

    @Query("SELECT * from games")
    fun getAllGames(): List<Game>
}