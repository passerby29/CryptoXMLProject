package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class LoadCoinsUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke() = repository.loadCoins()
}