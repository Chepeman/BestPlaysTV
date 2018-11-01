package com.jcdev.bestplaystv.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jcdev.bestplaystv.R
import kotlinx.coroutines.*
import org.json.JSONObject

class MainActivity : PlaysActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            val gamesRequest = playsTransport.getGames()
            val gamesResponse = gamesRequest.await()
            if(gamesResponse.isSuccessful) {
                val gamesEncoded = gamesResponse.body()
                val debug = true
            } else {
                val responseError = gamesResponse.errorBody()
                val debugError = true;
            }
        }
    }
}
