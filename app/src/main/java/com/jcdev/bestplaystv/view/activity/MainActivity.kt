package com.jcdev.bestplaystv.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.view.adapter.PopularGamesListAdapter
import com.jcdev.bestplaystv.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_plays.*


class MainActivity : PlaysActivity() {

    private lateinit var gameListListAdapter: PopularGamesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        gameListListAdapter = PopularGamesListAdapter(ArrayList(0))
        { game: Game, imageGame : ImageView ->  onPopularGameClicked(game, imageGame)}
        popularGamesView.layoutManager = GridLayoutManager(this, 2)
        popularGamesView.adapter = gameListListAdapter
        viewModel.popularVideoGames.observe(this, Observer {
            gameListListAdapter.loadItems(it!!.toList())
        })
        viewModel.requestMostPopularGames()


        searchIcon.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
            overridePendingTransition(0,0)
        }


    }

    private fun onPopularGameClicked(game : Game, imageGame: ImageView) {
        Log.d("[Plays.TV]", "Clicked! " + game.title)
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("game", game)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
