package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.passerby.data.repos.MainRepositoryImpl
import dev.passerby.domain.usecases.GetCoinInfoUseCase

class CoinInfoViewModel(
    private val application: Application,
    private val coinId: String
) : AndroidViewModel(application) {

    private val repository = MainRepositoryImpl(application)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)

    val coinInfo = getCoinInfoUseCase(coinId)
}