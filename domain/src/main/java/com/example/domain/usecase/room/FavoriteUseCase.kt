package com.example.domain.usecase.room

import com.example.domain.model.FavoriteProject
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllFavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(): Flow<List<FavoriteProject>> {
        return favoriteRepository.getAllFavorites()
    }
}

class InsertFavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(favoriteProject: FavoriteProject) {
        favoriteRepository.insertFavorite(favoriteProject)
    }
}

class DeleteFavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(id: Int) {
        return favoriteRepository.deleteFavorite(id)
    }
}

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return favoriteRepository.isFavorite(id)
    }
}