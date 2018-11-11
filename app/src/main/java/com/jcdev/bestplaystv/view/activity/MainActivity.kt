package com.jcdev.bestplaystv.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.view.adapter.PopularGamesListAdapter
import com.jcdev.bestplaystv.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PlaysActivity() {

    lateinit var gameListListAdapter: PopularGamesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        gameListListAdapter = PopularGamesListAdapter(ArrayList<Game>(0), { game: Game ->  onPopularGameClicked(game)})
        popularGamesView.layoutManager = GridLayoutManager(this, 2)
        popularGamesView.adapter = gameListListAdapter
        viewModel.popularVideoGames.observe(this, Observer {
            gameListListAdapter.loadItems(it!!.toList())
        })
        viewModel.requestMostPopularGames()


    }

    fun onPopularGameClicked(game : Game) {
        Log.d("[Plays.TV]", "Clicked! " + game.title)
    }
}
