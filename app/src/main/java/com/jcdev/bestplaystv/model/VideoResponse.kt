package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("content")
    val content: VideoContent
)

data class VideoContent(
    @SerializedName("result_count")
    val resultCount : Int,
    @SerializedName("items")
    val items: List<Video>
)