package com.jcdev.bestplaystv.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.viewmodel.MainViewModel
import kotlinx.coroutines.*
import org.json.JSONObject

class MainActivity : PlaysActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)


        viewModel.requestMostPopularGames()
    }
}
