package com.example.data.repositoryImpl

import com.example.data.local.dao.FavoriteDAO
import com.example.data.local.toDomain
import com.example.data.local.toEntity
import com.example.domain.model.FavoriteProject
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val dao: FavoriteDAO) :
    FavoriteRepository {
    override fun getAllFavorites(): Flow<List<FavoriteProject>> {
        return dao.getAllFavorites().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertFavorite(favorite: FavoriteProject) {
        withContext(Dispatchers.IO) {
            dao.addFavorites(favorite.toEntity())
        }
    }

    override suspend fun deleteFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            dao.removeFromFavorites(id)
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            dao.isFavorite(id)
        }
    }
}