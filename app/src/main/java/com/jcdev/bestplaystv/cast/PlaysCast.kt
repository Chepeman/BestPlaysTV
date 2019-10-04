package com.jcdev.bestplaystv.cast

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.common.images.WebImage
import com.jcdev.bestplaystv.model.Video

class PlaysCast(context: Context) :
    CastPlayer.SessionAvailabilityListener {
    private var castContext: CastContext = CastContext.getSharedInstance(context)
    private var castPlayer: CastPlayer
    private var mediaItems: Array<MediaQueueItem>? = null
    private var isCastReady = false

    init {
        castPlayer = CastPlayer(castContext)
        castPlayer.setSessionAvailabilityListener(this)
    }

    override fun onCastSessionAvailable() {
        isCastReady = true
    }

    override fun onCastSessionUnavailable() {
        if (mediaItems != null) {
            mediaItems = null
        }
        isCastReady = false
    }

    fun connectChromecast(video: Video) {
        val resolutions = video.resolutions.split("").toList()
        val videoUrl = "https://d1playscdntv-a.akamaihd.net/media/"+
                video.videoId +
                "/transcoded/" +
                resolutions.last() +
                ".mp4"

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

    fun isCastReady(): Boolean{
        return isCastReady
    }
}