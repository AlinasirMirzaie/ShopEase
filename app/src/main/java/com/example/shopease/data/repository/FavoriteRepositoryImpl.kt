package com.example.shopease.data.repository

import com.example.shopease.data.local.dao.FavoriteDao
import com.example.shopease.data.local.entity.FavoriteEntity
import com.example.shopease.domain.model.FavoriteProduct
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<FavoriteProduct>> =
        dao.getAllFavorites().map { list ->
            list.map { FavoriteProduct(it.productId, it.title, it.price, it.imageUrl) }
        }

    override fun isFavorite(productId: Int): Flow<Boolean> =
        dao.observeFavorite(productId).map { it != null }

    override suspend fun toggleFavorite(product: Product) {
        val existing = dao.getFavorite(product.id)
        if (existing != null) {
            dao.delete(product.id)
        } else {
            dao.insert(
                FavoriteEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    imageUrl = product.imageUrl
                )
            )
        }
    }
}