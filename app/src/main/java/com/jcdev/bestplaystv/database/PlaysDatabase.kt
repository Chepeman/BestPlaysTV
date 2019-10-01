package com.jcdev.bestplaystv.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jcdev.bestplaystv.model.Game

@Database(
    entities = [Game::class],
    version = PlaysDatabase.VERSION
)

abstract class PlaysDatabase : RoomDatabase() {

    abstract val gameDao: GamesDao

    companion object {
        const val VERSION = 1
        const val NAME = "playsDatabase"
    }

}