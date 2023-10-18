package dev.passerby.cryptoxmlproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.passerby.cryptoxmlproject.callbacks.FavoritesDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemFavoriteCoinBinding
import dev.passerby.cryptoxmlproject.viewholders.FavoritesViewHolder
import dev.passerby.domain.models.CoinModel

class FavoritesAdapter : ListAdapter<CoinModel, FavoritesViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView =
            ItemFavoriteCoinBinding.inflate(layoutInflater, parent, false)
        return FavoritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.favoriteNameTexView.text = item.name
    }
}