package com.jcdev.bestplaystv.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.view.adapter.PopularGamesListAdapter
import com.jcdev.bestplaystv.view.adapter.RandomGameVideoAdapter
import com.jcdev.bestplaystv.viewmodel.GameDetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*
import kotlinx.android.synthetic.main.listitem_related_video.*


class GameDetailActivity : PlaysActivity() {

    private lateinit var game : Game
    private lateinit var videoAdapter : RandomGameVideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_left_in_100, R.anim.slide_left_out_50)
        setContentView(R.layout.activity_game_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = ""

        val bundle = intent.extras
        if(bundle != null && bundle.containsKey("game")) {
            game = bundle.getSerializable("game") as Game

            //InitModelView
            val viewModel = ViewModelProviders.of(this, viewModelFactory { GameDetailViewModel(game) })
                .get(GameDetailViewModel::class.java)

            gameTitleText.text = game.title
            gameTitleViews.text = game.stats.videos.toString() + " Views"
            Picasso.get()
                .load("https:" + game.thumbnail.replace("exmedium", "exlarge"))
                .fit()
                .noFade()
                .centerCrop()
                .into(gameImage, object : Callback {
                    override fun onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    override fun onError(e: Exception?) {
                        supportStartPostponedEnterTransition();
                    }
                })

            videoAdapter = RandomGameVideoAdapter(ArrayList(0))
            suggestedPlaysView.layoutManager = LinearLayoutManager(this)
            suggestedPlaysView.adapter = videoAdapter
            viewModel.randomGameVideos.observe(this, Observer {
                videoAdapter.loadItems(it!!.toList())
            })
            viewModel.getRandomGameVideos()
        } else {
            finishAfterTransition()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_right_in_50, R.anim.slide_right_out_100)
        finish()
    }


}
