package com.jcdev.bestplaystv.transport

import android.content.Context
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.GameResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class PlaysTransport(private val transport: Transport, private val context: Context) {

    fun getGames() : Deferred<Response<GameResponse>> {
        return  transport.getGames(context.getString(R.string.app_id), context.getString(R.string.app_key))
    }
}