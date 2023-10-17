package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class SearchCoinsUseCase(private val repository: MainRepository) {

    operator fun invoke(filter: String) = repository.searchCoins(filter)
}