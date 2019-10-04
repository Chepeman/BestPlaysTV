package com.jcdev.bestplaystv.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("stats")
    val stats: Stats
) : Serializable