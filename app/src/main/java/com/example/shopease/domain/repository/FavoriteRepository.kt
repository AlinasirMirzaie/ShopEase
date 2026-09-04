package com.example.shopease.domain.repository

import com.example.shopease.domain.model.FavoriteProduct
import com.example.shopease.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteProduct>>
    fun isFavorite(productId: Int): Flow<Boolean>
    suspend fun toggleFavorite(product: Product)
}