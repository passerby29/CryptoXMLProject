package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class GetTopCoinsListUseCase(
    private val repository: MainRepository
) {

    operator fun invoke() = repository.getTopCoinList()
}