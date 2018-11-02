package com.jcdev.bestplaystv.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import kotlinx.coroutines.*
import org.json.JSONObject

class MainActivity : PlaysActivity() {

    lateinit var popularVideoGames : ArrayList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            val gamesRequest = playsTransport.getGames()
            val gamesResponse = gamesRequest.await()
            if(gamesResponse.isSuccessful) {
                val gamesEncoded = gamesResponse.body()
                val debug = true


                gamesEncoded?.content?.games?.asSequence()?.filter { it.value.stats.videos > 300 }?.forEach {
                    popularVideoGames.add(it.value)
                }

            } else {
                val responseError = gamesResponse.errorBody()
                val debugError = true;
            }
        }
    }
}
