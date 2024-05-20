package models


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CryptoCoin(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @SerialName("age")
    val age: Int? = 0,
    @SerialName("allTimeHighUSD")
    val allTimeHighUSD: Double? = 0.0,
    @SerialName("cap")
    val cap: Long? = 0,
    @SerialName("categories")
    val categories: Set<String> = mutableSetOf<String>(),
    @SerialName("circulatingSupply")
    val circulatingSupply: Double? = 0.0,
    @SerialName("code")
    val code: String? = "",
    @SerialName("color")
    val color: String? = "",
    @SerialName("delta")
    @Embedded()
    val delta: Delta? = Delta(),
    @SerialName("exchanges")
    val exchanges: Long? = 0,
    @SerialName("links")
    @Embedded
    val links: Links? = Links(),
    @SerialName("markets")
    val markets: Long? = 0,
    @SerialName("maxSupply")
    val maxSupply: Long? = 0,
    @SerialName("name")
    val name: String? = "",
    @SerialName("pairs")
    val pairs: Long? = 0,
    @SerialName("png32")
    val png32: String? = "",
    @SerialName("png64")
    val png64: String? = "",
    @SerialName("rank")
    val rank: Long? = 0,
    @SerialName("rate")
    val rate: Double? = 0.0,
    @SerialName("symbol")
    val symbol: String? = "",
    @SerialName("totalSupply")
    val totalSupply: Double? = 0.0,
    @SerialName("volume")
    val volume: Long? = 0,
    @SerialName("webp32")
    val webp32: String? = "",
    @SerialName("webp64")
    val webp64: String? = ""
)