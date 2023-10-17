package dev.passerby.data.models.dto


import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("availableSupply")
    val availableSupply: Long,
    @SerializedName("contractAddress")
    val contractAddress: String?,
    @SerializedName("decimals")
    val decimals: Int?,
    @SerializedName("exp")
    val exp: List<String>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("marketCap")
    val marketCap: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("priceBtc")
    val priceBtc: Double,
    @SerializedName("priceChange1d")
    val priceChange1d: Double,
    @SerializedName("priceChange1h")
    val priceChange1h: Double,
    @SerializedName("priceChange1w")
    val priceChange1w: Double,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("redditUrl")
    val redditUrl: String?,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("totalSupply")
    val totalSupply: Long,
    @SerializedName("twitterUrl")
    val twitterUrl: String?,
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("websiteUrl")
    val websiteUrl: String
)