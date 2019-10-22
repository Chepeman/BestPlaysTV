package com.jcdev.bestplaystv.transport

import com.jcdev.bestplaystv.model.response.GameResponse
import com.jcdev.bestplaystv.model.response.UserResponse
import com.jcdev.bestplaystv.model.response.VideoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaysTransport {
    @GET("data/v1/games")
    fun getGamesAsync(): Deferred<Response<GameResponse>>

    @GET("data/v1/videos/search")
    fun getRandomVideosByIdAsync(
        @Query("gameId") gameId: String
    ): Deferred<Response<VideoResponse>>

    @GET("data/v1/videos/search")
    fun getUserVideosByIdAsync(
        @Query("userId") userId: String
    ): Deferred<Response<VideoResponse>>


    @GET("/data/v1/users/{username}")
    fun getUserAsync(
        @Path("username") username: String
    ): Deferred<Response<UserResponse>>
}