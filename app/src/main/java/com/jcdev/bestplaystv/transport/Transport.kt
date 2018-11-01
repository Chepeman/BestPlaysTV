package com.jcdev.bestplaystv.transport

import com.jcdev.bestplaystv.model.GameResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Transport {
    @GET("data/v1/games")
    fun getGames(
        @Query("appid") appId: String,
        @Query("appkey") appKey: String
    ) : Deferred<Response<GameResponse>>


    /*
    @GET("characters?orderBy=-modified&nameStartsWith=th")
    fun getCharacters(
            @Query("ts") ts: Long,
            @Query("apikey") apiKey: String,
            @Query("hash") hash: String,
            @Query("limit") limit: Int
            ): Observable<MarvelResponse<Character>>
     */
}