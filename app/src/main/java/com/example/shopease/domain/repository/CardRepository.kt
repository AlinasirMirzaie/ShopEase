package com.example.shopease.domain.repository


import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: Int)
    suspend fun updateQuantity(productId: Int, quantity: Int)
    suspend fun clearCart()
}