package com.jcdev.bestplaystv.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jcdev.bestplaystv.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : PlaysViewModel() {


    private val _popularVideoGames : MutableLiveData<ArrayList<Game>> = MutableLiveData()
    val popularVideoGames : LiveData<ArrayList<Game>>
    get() {
         return _popularVideoGames
    }


    fun requestMostPopularGames() {
        GlobalScope.launch(Dispatchers.Main) {
            var videoGamesList = ArrayList<Game>()
            val gamesRequest = playsTransport.getGames()
            val gamesResponse = gamesRequest.await()
            if(gamesResponse.isSuccessful) {
                val gamesEncoded = gamesResponse.body()

                gamesEncoded?.content?.games?.asSequence()?.filter { it.value.stats.videos > 1000 }?.forEach {
                    videoGamesList.add(it.value)
                }

                _popularVideoGames.value = videoGamesList

            } else {
                val responseError = gamesResponse.errorBody()
            }
        }
    }
}