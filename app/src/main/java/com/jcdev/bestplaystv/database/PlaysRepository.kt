package com.jcdev.bestplaystv.database

import com.jcdev.bestplaystv.model.Game

class PlaysRepository(private val gamesDao: GamesDao) {
    fun getGamesBySearch(query: String): List<Game> {
        return gamesDao.getGamesBySearch("%$query%")
    }

    fun insertGame(game: Game) {
        gamesDao.insertGame(game)
    }

    fun getGameById(id: String): Game {
        return gamesDao.getGameById(id)
    }

    fun getAllGames(): List<Game> {
        return gamesDao.getAllGames()
    }
}