package dev.passerby.cryptoxmlproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.passerby.data.repos.MainRepositoryImpl
import dev.passerby.domain.usecases.GetCoinsListUseCase
import dev.passerby.domain.usecases.GetFavoritesUseCase
import dev.passerby.domain.usecases.GetTopCoinsListUseCase
import dev.passerby.domain.usecases.LoadCoinsUseCase
import dev.passerby.domain.usecases.SearchCoinsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepositoryImpl(application)
    private val getCoinsListUseCase = GetCoinsListUseCase(repository)
    private val getTopCoinsListUseCase = GetTopCoinsListUseCase(repository)
    private val getFavoritesUseCase = GetFavoritesUseCase(repository)
    private val searchCoinsUseCase = SearchCoinsUseCase(repository)
    private val loadCoinsUseCase = LoadCoinsUseCase(repository)

    val coinsList = getCoinsListUseCase()
    val topCoinsList = getTopCoinsListUseCase()
    val favoritesList = getFavoritesUseCase()

    init {
        viewModelScope.launch {
            loadCoinsUseCase()
        }
    }

    fun searchCoins(filter: String) {
        viewModelScope.launch {
            searchCoinsUseCase(filter)
        }
    }
}