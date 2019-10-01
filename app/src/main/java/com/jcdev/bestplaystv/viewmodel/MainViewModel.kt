package com.jcdev.bestplaystv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcdev.bestplaystv.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : PlaysViewModel() {
    private val viewModelJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    private val _popularVideoGames: MutableLiveData<ArrayList<Game>> = MutableLiveData()
    val popularVideoGames: LiveData<ArrayList<Game>>
        get() {
            return _popularVideoGames
        }


    fun requestMostPopularGames() {
        serviceScope.launch {
            val videoGamesList = ArrayList<Game>()
            val gamesRequest = playsTransport.getGames()
            val gamesResponse = gamesRequest.await()
            if (gamesResponse.isSuccessful) {
                val gamesEncoded = gamesResponse.body()

                gamesEncoded?.content?.games?.asSequence()
                    ?.sortedByDescending { it.value.stats.videos }?.let { games ->
                        games.take(20)?.forEach {
                            it.value.thumbnail.replace("exmedium", "exlarge")
                            videoGamesList.add(it.value)
                        }

                        insertAllGamesInDB(games)
                    }

                _popularVideoGames.postValue(videoGamesList)

            } else {
                val responseError = gamesResponse.errorBody()
            }
        }
    }

    private fun insertAllGamesInDB(games: Sequence<Map.Entry<String, Game>>) {
        games.forEach {
            playsRepository.insertGame(it.value)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}