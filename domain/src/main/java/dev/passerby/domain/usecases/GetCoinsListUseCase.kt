package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class GetCoinsListUseCase(
    private val repository: MainRepository
) {

    operator fun invoke() = repository.getCoinList()
}