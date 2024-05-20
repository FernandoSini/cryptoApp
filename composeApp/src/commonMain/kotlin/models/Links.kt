package models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Links(
    @SerialName("discord")
    val discord: String? = "",
    @SerialName("instagram")
    val instagram: String? = "",
    @SerialName("linkedin")
    val linkedin: String? = "",
    @SerialName("medium")
    val medium: String? = "",
    @SerialName("naver")
    val naver: String? = "",
    @SerialName("reddit")
    val reddit: String? = "",
    @SerialName("soundcloud")
    val soundcloud: String? = "",
    @SerialName("spotify")
    val spotify: String? = "",
    @SerialName("telegram")
    val telegram: String? = "",
    @SerialName("tiktok")
    val tiktok: String ?= "",
    @SerialName("twitch")
    val twitch: String? = "",
    @SerialName("twitter")
    val twitter: String? = "",
    @SerialName("website")
    val website: String? = "",
    @SerialName("wechat")
    val wechat: String? = "",
    @SerialName("whitepaper")
    val whitepaper: String? = "",
    @SerialName("youtube")
    val youtube: String? = ""
)