package com.galih.cookvegie.data.source.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(";").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ";")
    }
}