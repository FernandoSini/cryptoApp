package models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0

)
