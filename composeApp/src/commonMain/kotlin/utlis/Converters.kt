package utlis

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun SetToJson(value: Set<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToSet(value: String) = Json.decodeFromString<Set<String>>(value)
}