package com.example.shopease.data.repository

import com.example.shopease.data.local.dao.CartDao
import com.example.shopease.data.local.entity.CartItemEntity
import com.example.shopease.data.mapper.toDomain
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<com.example.shopease.domain.model.CartItem>> =
        dao.getCartItems().map { items -> items.map { it.toDomain() } }

    override suspend fun addToCart(product: Product) {
        val existing = dao.getCartItem(product.id)
        if (existing != null) {
            dao.updateQuantity(product.id, existing.quantity + 1)
        } else {
            dao.upsert(
                CartItemEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    quantity = 1
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        dao.deleteItem(productId)
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            dao.deleteItem(productId)
        } else {
            dao.updateQuantity(productId, quantity)
        }
    }

    override suspend fun clearCart() {
        dao.clearCart()
    }
}