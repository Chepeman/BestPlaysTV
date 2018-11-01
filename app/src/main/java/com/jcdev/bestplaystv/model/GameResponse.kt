package com.jcdev.bestplaystv.model
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class GameResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("content")
    val content: Content
)

data class Content(
    @SerializedName("games")
    val games: LinkedHashMap<String, Game>,
    @SerializedName("total")
    val total: Int
)