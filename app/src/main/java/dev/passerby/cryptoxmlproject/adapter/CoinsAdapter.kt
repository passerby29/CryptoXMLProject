package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.callbacks.CoinDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemCoinBinding
import dev.passerby.cryptoxmlproject.viewholders.CoinViewHolder
import dev.passerby.domain.models.CoinModel
import java.math.RoundingMode
import java.text.DecimalFormat

class CoinsAdapter(private val context: Context) :
    ListAdapter<CoinModel, CoinViewHolder>(CoinDiffCallback()) {

    interface CoinClickListener{
        fun coinCLick(itemId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val itemView =
            ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(binding) {
            Glide.with(context).load(item.icon).into(coinLogoImageView)
            coinNameTextView.text = item.name
            coinSymbolTextView.text = item.symbol
            coinPriceTextView.text = roundDouble(item.price)
            coinChangeTextView.text = item.priceChange1h.toString()
        }
    }

    private fun roundDouble(double: Double): String {
        val decimalFormat = DecimalFormat(
            if (double >= 10) {
                DECIMAL_FORMAT_PATTERN_TWO
            } else {
                DECIMAL_FORMAT_PATTERN_FOUR
            }
        )
        decimalFormat.roundingMode = RoundingMode.DOWN
        return decimalFormat.format(double)
    }

    companion object {
        private const val DECIMAL_FORMAT_PATTERN_FOUR = "#.####"
        private const val DECIMAL_FORMAT_PATTERN_TWO = "#.##"
    }
}