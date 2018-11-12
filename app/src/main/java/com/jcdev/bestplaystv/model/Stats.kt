package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stats(
    @SerializedName("videos")
    val videos: Int
) : Serializable