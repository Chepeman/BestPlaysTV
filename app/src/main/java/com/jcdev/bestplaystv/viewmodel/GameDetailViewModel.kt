package com.jcdev.bestplaystv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameDetailViewModel(val game : Game) : PlaysViewModel() {

    private val _randomGameVideos : MutableLiveData<List<Video>> = MutableLiveData()
    val randomGameVideos : LiveData<List<Video>>
        get() {
            return _randomGameVideos
        }

    fun getRandomGameVideos() {
        GlobalScope.launch(Dispatchers.Main) {

            val videoRequest = playsTransport.getRandomVideosById(gameId = game.id)
            val videoResponse = videoRequest.await()
            if(videoResponse.isSuccessful) {
                val videoEnconded = videoResponse.body()

                _randomGameVideos.value = videoEnconded?.content?.items

            } else {
                val responseError = videoResponse.errorBody()
            }
        }
    }
}