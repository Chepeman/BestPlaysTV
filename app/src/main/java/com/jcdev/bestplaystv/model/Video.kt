package com.jcdev.bestplaystv.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Video(
    @SerializedName("author")
    val author: Author,
    @SerializedName("description")
    val description: String,
    @SerializedName("embed")
    val embed: String,
    @SerializedName("game")
    val game: Game,
    @SerializedName("hashtags")
    val hashtags: List<Hashtag>,
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("metatags")
    val metatags: List<Metatag>,
    @SerializedName("resolutions")
    val resolutions: String,
    @SerializedName("stats")
    val stats: VideoStats,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("upload_time")
    val uploadTime: String,
    @SerializedName("video_id")
    val videoId: String
) : Serializable

data class Hashtag(
    @SerializedName("author")
    val author: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("tag")
    val tag: String
) : Serializable

data class Author(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("stats")
    val stats: UserStats
) : Serializable



data class Metatag(
    @SerializedName("metatag")
    val metatag: String
)