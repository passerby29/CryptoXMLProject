package dev.passerby.data.mappers

import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.dto.Coin
import dev.passerby.domain.models.CoinModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class CoinMapper {

    fun mapDtoToDbModel(dto: Coin) = CoinDbModel(
        availableSupply = dto.availableSupply,
        contractAddress = dto.contractAddress,
        decimals = dto.decimals,
        exp = dto.exp,
        icon = dto.icon,
        id = dto.id,
        marketCap = dto.marketCap,
        name = dto.name,
        price = roundDouble(dto.price),
        priceBtc = dto.priceBtc,
        priceChange1d = dto.priceChange1d,
        priceChange1h = dto.priceChange1h,
        priceChange1w = dto.priceChange1w,
        rank = dto.rank,
        redditUrl = dto.redditUrl,
        symbol = dto.symbol,
        totalSupply = dto.totalSupply,
        twitterUrl = dto.twitterUrl,
        volume = dto.volume,
        websiteUrl = dto.websiteUrl,
    )

    fun mapDbModelToEntity(dbModel: CoinDbModel) = CoinModel(
        availableSupply = dbModel.availableSupply,
        contractAddress = dbModel.contractAddress,
        decimals = dbModel.decimals,
        exp = dbModel.exp,
        icon = dbModel.icon,
        id = dbModel.id,
        marketCap = dbModel.marketCap,
        name = dbModel.name,
        price = dbModel.price,
        priceBtc = dbModel.priceBtc,
        priceChange1d = dbModel.priceChange1d,
        priceChange1h = dbModel.priceChange1h,
        priceChange1w = dbModel.priceChange1w,
        rank = dbModel.rank,
        redditUrl = dbModel.redditUrl,
        symbol = dbModel.symbol,
        totalSupply = dbModel.totalSupply,
        twitterUrl = dbModel.twitterUrl,
        volume = dbModel.volume,
        websiteUrl = dbModel.websiteUrl,
    )

    private fun roundDouble(double: Double): Double {
        val locale = Locale("en", "UK")
        val pattern = if (double >= 10) {
            DECIMAL_FORMAT_PATTERN_TWO
        } else {
            DECIMAL_FORMAT_PATTERN_FOUR
        }
        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)

        return decimalFormat.format(double).toDouble()
    }

    companion object {
        private const val DECIMAL_FORMAT_PATTERN_FOUR = "###.####"
        private const val DECIMAL_FORMAT_PATTERN_TWO = "###.##"
    }
}