package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName


data class Game(
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("stats")
    val stats: Stats,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String
)

data class Stats(
    @SerializedName("videos")
    val videos: Int
)




