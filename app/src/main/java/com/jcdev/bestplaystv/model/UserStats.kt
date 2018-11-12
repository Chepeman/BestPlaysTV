package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName

data class UserStats(
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("videos")
    val videos: Int
)