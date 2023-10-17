package dev.passerby.data.models.dto


import com.google.gson.annotations.SerializedName
import dev.passerby.data.models.dto.Coin

data class CoinsDto(
    @SerializedName("coins")
    val coins: List<Coin>
)