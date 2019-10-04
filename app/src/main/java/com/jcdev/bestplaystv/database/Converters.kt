package com.jcdev.bestplaystv.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.jcdev.bestplaystv.model.Stats

class Converters {
    private val gson = GsonBuilder().serializeNulls().create()

    @TypeConverter
    fun toStats(value: String): Stats {
        return gson.fromJson(value, Stats::class.java)
    }

    @TypeConverter
    fun fromStats(stats: Stats): String {
        return gson.toJson(stats, Stats::class.java)
    }

}