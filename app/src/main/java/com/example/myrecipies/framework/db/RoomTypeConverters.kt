package com.example.myrecipies.framework.db

import android.util.Log
import androidx.room.TypeConverter

class RoomTypeConverters {
    @TypeConverter
    fun stringToStringList(data: String?): List<String>? {
        return data?.let {
            it.split(",").map {
                try {
                    it
                } catch (ex: NumberFormatException) {
                    Log.e("Exception", "Cannot convert $it to number", ex)
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    fun stringListToString(strings: List<String>?): String? {
        return strings?.joinToString(",")
    }
}