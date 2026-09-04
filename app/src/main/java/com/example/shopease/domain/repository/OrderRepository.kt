package com.example.shopease.domain.repository

import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    suspend fun placeOrder(items: List<CartItem>, total: Double)
}