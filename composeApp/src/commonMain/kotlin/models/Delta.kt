package models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Delta(
    @SerialName("day")
    val day: Double? = 0.0,
    @SerialName("hour")
    val hour: Double? = 0.0,
    @SerialName("month")
    val month: Double? = 0.0,
    @SerialName("quarter")
    val quarter: Double? = 0.0,
    @SerialName("week")
    val week: Double? = 0.0,
    @SerialName("year")
    val year: Double? = 0.0
)