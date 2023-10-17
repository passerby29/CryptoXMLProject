package dev.passerby.domain.repos

import androidx.lifecycle.LiveData
import dev.passerby.domain.models.CoinModel

interface MainRepository {

    fun getCoinList(): LiveData<List<CoinModel>>
    fun getTopCoinList(): LiveData<List<CoinModel>>
    fun getFavorites(): LiveData<List<CoinModel>>
    fun searchCoins(filter: String): LiveData<List<CoinModel>>

    suspend fun loadCoins()
}