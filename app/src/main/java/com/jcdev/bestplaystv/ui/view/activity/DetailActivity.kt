package com.jcdev.bestplaystv.ui.view.activity

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.common.images.WebImage
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.User
import com.jcdev.bestplaystv.model.Video
import com.jcdev.bestplaystv.ui.view.adapter.VideoAdapter
import com.jcdev.bestplaystv.ui.viewmodel.DetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*


class DetailActivity : PlaysActivity(), CastPlayer.SessionAvailabilityListener {
    private lateinit var id: String
    private lateinit var type: String
    private lateinit var videoAdapter: VideoAdapter

    private lateinit var castContext: CastContext
    private lateinit var castPlayer: CastPlayer
    private var mediaItems: Array<MediaQueueItem>? = null
    private var isCastReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_left_in_100, R.anim.slide_left_out_50)
        setContentView(R.layout.activity_game_detail)
        setSupportActionBar(toolbar)

        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory {
            DetailViewModel(
                id,
                type
            )
        }).get(DetailViewModel::class.java)

        setupSnackbarObserver(viewModel)

        viewModel.randomGameVideos.observe(this, Observer {
            videoAdapter.loadItems(it)
        })

        viewModel.getRandomGameVideos()
    }

    private fun setupUI() {
        castContext = CastContext.getSharedInstance(this)
        castPlayer = CastPlayer(castContext)
        castPlayer.setSessionAvailabilityListener(this)
        isCastReady = castPlayer.isCastSessionAvailable

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = ""

        val bundle = intent.extras
        if (bundle != null && (bundle.containsKey("game") || bundle.containsKey("user"))) {

            getTypeAndId(bundle)

            videoAdapter =
                VideoAdapter(ArrayList(0)) { video: Video ->
                    onVideoClicked(video)
                }
            videoAdapter.setCastReady(castPlayer.isCastSessionAvailable)
            suggestedPlaysView.layoutManager = LinearLayoutManager(this)
            suggestedPlaysView.adapter = videoAdapter

        } else {
            finishAfterTransition()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return super.onPrepareOptionsMenu(menu)
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
        connectChromecast(video)
    }

    override fun onCastSessionAvailable() {
        isCastReady = true
        videoAdapter.setCastReady(true)
    }

    override fun onCastSessionUnavailable() {
        if (mediaItems != null) {
            mediaItems = null
        }
        isCastReady = false
        videoAdapter.setCastReady(false)
    }

    private fun getTypeAndId(bundle: Bundle) {
        if (bundle.containsKey("game")) {
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
                        supportStartPostponedEnterTransition()
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
    }

    private fun connectChromecast(video: Video) {
        val resolutions = video.resolutions.split(",").toMutableList()
        resolutions.remove("1080") //Some chromecast doesn't play 1080p videos.


        var videoUrl = "https://d1playscdntv-a.akamaihd.net/media/" +
                video.videoId + "/transcoded/" +
                resolutions.first() +
                ".mp4"

        if (video.videoId.length != 24) {
            videoUrl = videoUrl
                .replace("/media/", "/video/")
                .replace("/transcoded/", "/processed/")
        }

        val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
        movieMetadata.putString(MediaMetadata.KEY_TITLE, video.description)
        movieMetadata.putString(MediaMetadata.KEY_ALBUM_ARTIST, video.author.id)
        movieMetadata.addImage(WebImage(Uri.parse("https://images.idgesg.net/images/article/2018/10/playstvbig-100776869-large.jpg")))
        val mediaInfo = MediaInfo.Builder(videoUrl)
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType(MimeTypes.VIDEO_UNKNOWN)
            .setMetadata(movieMetadata).build()


        mediaItems = arrayOf(MediaQueueItem.Builder(mediaInfo).build())
        if (isCastReady && mediaItems != null) {
            castPlayer.loadItems(mediaItems, 0, 0, Player.REPEAT_MODE_OFF)
        }
    }
}
