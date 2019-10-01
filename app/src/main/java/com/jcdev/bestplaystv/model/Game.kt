package com.jcdev.bestplaystv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "games")
data class Game (
    @PrimaryKey @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "link")
    @SerializedName("link")
    val link: String,
    @ColumnInfo(name = "stats")
    @SerializedName("stats")
    val stats: Stats,
    @ColumnInfo(name = "thumbnail")
    @SerializedName("thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String
) : Serializable




