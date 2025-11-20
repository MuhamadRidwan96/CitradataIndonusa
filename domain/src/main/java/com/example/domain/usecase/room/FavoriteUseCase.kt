package com.example.domain.usecase.room

import com.example.domain.di.IoDispatcher
import com.example.domain.model.FavoriteProject
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetAllFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<List<FavoriteProject>> {
        return withContext(dispatcher) { favoriteRepository.getAllFavorites() }
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