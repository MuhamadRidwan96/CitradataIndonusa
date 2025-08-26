package com.example.domain.repository

import com.example.domain.model.FavoriteProject
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
   fun getAllFavorites(): Flow<List<FavoriteProject>>
   suspend fun insertFavorite(favorite: FavoriteProject)
   suspend fun deleteFavorite(id: Int)
   suspend fun isFavorite(id: Int) :Boolean
}
