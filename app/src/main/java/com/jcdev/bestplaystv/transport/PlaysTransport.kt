package com.jcdev.bestplaystv.transport

import android.content.Context
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.GameResponse
import com.jcdev.bestplaystv.model.UserResponse
import com.jcdev.bestplaystv.model.VideoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class PlaysTransport(private val transport: Transport, private val context: Context) {

    fun getGames(): Deferred<Response<GameResponse>> {
        return transport.getGames(
            context.getString(R.string.app_id),
            context.getString(R.string.app_key)
        )
    }

    fun getRandomVideosById(gameId: String): Deferred<Response<VideoResponse>> {
        return transport.getRandomVideosById(
            context.getString(R.string.app_id),
            context.getString(R.string.app_key),
            gameId
        )
    }

    fun getUser(username: String): Deferred<Response<UserResponse>> {
        return transport.getUser(
            username,
            context.getString(R.string.app_id),
            context.getString(R.string.app_key)
        )
    }
}