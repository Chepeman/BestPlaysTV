package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName
import com.jcdev.bestplaystv.viewmodel.PlaysViewModel

data class VideoResponse(
    @SerializedName("result_count")
    val resultCount: Int,
    @SerializedName("content")
    val items: List<Video>
)