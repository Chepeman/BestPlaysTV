package com.jcdev.bestplaystv.transport

import com.jcdev.bestplaystv.model.GameResponse
import com.jcdev.bestplaystv.model.UserResponse
import com.jcdev.bestplaystv.model.VideoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Transport {
    @GET("data/v1/games")
    fun getGames(
        @Query("appid") appId: String,
        @Query("appkey") appKey: String
    ) : Deferred<Response<GameResponse>>

    @GET("data/v1/videos/search")
    fun getRandomVideosById(
        @Query("appid") appId: String,
        @Query("appkey") appKey: String,
        @Query("gameId") gameId: String
    ) : Deferred<Response<VideoResponse>>

    @GET("data/v1/videos/search")
    fun getUserVideosById(
        @Query("appid") appId: String,
        @Query("appkey") appKey: String,
        @Query("userId") userId: String
    ) : Deferred<Response<VideoResponse>>


    @GET("/data/v1/users/{username}")
    fun getUser(
        @Path("username") username: String,
        @Query("appid") appId: String,
        @Query("appkey") appKey: String
    ) : Deferred<Response<UserResponse>>
}