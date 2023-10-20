package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class GetCoinInfoUseCase(
    private val repository: MainRepository
) {

    operator fun invoke(coinId: String) = repository.getCoinInfo(coinId)
}