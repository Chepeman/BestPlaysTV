package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoStats(
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("reposts")
    val reposts: Int,
    @SerializedName("views")
    val views: String
) : Serializable