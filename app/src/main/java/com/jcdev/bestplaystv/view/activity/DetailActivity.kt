package com.jcdev.bestplaystv.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.gms.cast.framework.CastButtonFactory
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.cast.PlaysCast
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.User
import com.jcdev.bestplaystv.model.Video
import com.jcdev.bestplaystv.transport.PlaysTransport
import com.jcdev.bestplaystv.view.adapter.VideoAdapter
import com.jcdev.bestplaystv.viewmodel.DetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*
import javax.inject.Inject


class DetailActivity : PlaysActivity(), CastPlayer.SessionAvailabilityListener {
    @Inject
    lateinit var playsCast: PlaysCast

    private lateinit var id : String
    private lateinit var type: String
    private lateinit var videoAdapter : VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_left_in_100, R.anim.slide_left_out_50)
        setContentView(R.layout.activity_game_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = ""

        val bundle = intent.extras
        if(bundle != null && (bundle.containsKey("game") || bundle.containsKey("user"))) {

            if(bundle.containsKey("game")) {
                val game = bundle.get("game") as Game
                id = game.id
                type = "game"
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
            } else {
                val user = bundle.get("user") as User
                id = user.id
                type = "user"
                gameTitleText.text = user.id
                gameTitleViews.text = user.stats.videos.toString() + " Views"
                Picasso.get()
                    .load("https:" + user.avatar.replace("exmedium", "exlarge"))
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
            }

            //InitModelView
            val viewModel = ViewModelProviders.of(this, viewModelFactory { DetailViewModel(id, type) })
                .get(DetailViewModel::class.java)

            videoAdapter = VideoAdapter(ArrayList(0)) {
                    video: Video ->  onVideoClicked(video)
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item);
        return true
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

    private fun onVideoClicked(video: Video) {
        playsCast.connectChromecast(video)
    }

    override fun onCastSessionAvailable() {
        videoAdapter.setCastReady(true)
    }

    override fun onCastSessionUnavailable() {
        videoAdapter.setCastReady(false)
    }
}
