package com.jcdev.bestplaystv.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.ui.view.adapter.PopularGamesListAdapter
import com.jcdev.bestplaystv.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_plays.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : PlaysActivity() {

    private lateinit var gameListListAdapter: PopularGamesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.jcdev.bestplaystv.R.layout.activity_main)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        searchIcon.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
            overridePendingTransition(0,0)
        }

        gameListListAdapter =
            PopularGamesListAdapter(ArrayList(0))
            { game: Game -> onPopularGameClicked(game) }
        popularGamesView.layoutManager = GridLayoutManager(this, 2)
        popularGamesView.adapter = gameListListAdapter
    }

    private fun setupObservers() {
        val viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        setupSnackbarObserver(viewModel)

        viewModel.popularVideoGames.observe(this, Observer {
            gameListListAdapter.loadItems(it)
        })

        viewModel.requestMostPopularGames(shouldReloadGameDatabase())
    }

    private fun onPopularGameClicked(game : Game) {
        Log.d("[Plays.TV]", "Clicked! " + game.title)
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("game", game)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun shouldReloadGameDatabase(): Boolean {
        val sharedPreferences = getSharedPreferences(
            "playsPreferences",
            Context.MODE_PRIVATE
        )
        val lastReloadHours = sharedPreferences.getLong("last_load", 0)
        val rightNow = Calendar.getInstance().timeInMillis
        return if (lastReloadHours == 0L) {
            sharedPreferences.edit().putLong("last_load", rightNow).apply()
            true
        } else {
            if (rightNow - lastReloadHours >= 86400000) {
                sharedPreferences.edit().putLong("last_load", rightNow).apply()
                true
            } else {
                false
            }
        }
    }
}
