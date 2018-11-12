package com.jcdev.bestplaystv.viewmodel

import com.jcdev.bestplaystv.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameDetailViewModel(val game : Game) : PlaysViewModel() {

    fun getRandomGameVideos() {
        GlobalScope.launch(Dispatchers.Main) {
            val videoRequest = playsTransport.getRandomVideosById(gameId = game.id)
            val videoResponse = videoRequest.await()
        }
    }
}