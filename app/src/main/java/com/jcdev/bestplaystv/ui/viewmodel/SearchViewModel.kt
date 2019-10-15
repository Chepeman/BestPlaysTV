package com.jcdev.bestplaystv.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.Video
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel() : PlaysViewModel() {
    private val viewModelJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    private val _gameList: MutableLiveData<MutableList<Any>> = MutableLiveData()
    val gameList: LiveData<MutableList<Any>>
        get() {
            return _gameList
        }

    fun gameAndUserSearch(textChanged: String) {
        serviceScope.launch {
            val resultList = mutableListOf<Any>()
            val gamesByQuery = playsRepository.getGamesBySearch(textChanged)
            val usersByQuery = playsTransport.getUser(textChanged).await()
            if(usersByQuery.isSuccessful) {
                usersByQuery.body()?.content?.user?.let { resultList.add(it) }
            }

            gamesByQuery.forEach {
                resultList.add(it)
            }

            _gameList.postValue(resultList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

