package dev.passerby.domain.usecases

import dev.passerby.domain.repos.MainRepository

class GetFavoritesUseCase(private val repository: MainRepository) {

    operator fun invoke() = repository.getFavorites()
}