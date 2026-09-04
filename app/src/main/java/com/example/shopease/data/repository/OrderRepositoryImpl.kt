package com.example.shopease.data.repository

import com.example.shopease.data.local.dao.OrderDao
import com.example.shopease.data.local.entity.OrderEntity
import com.example.shopease.data.mapper.toDomain
import com.example.shopease.data.remote.dto.OrderItemSnapshot
import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dao: OrderDao,
    private val json: Json
) : OrderRepository {

    override fun getOrders(): Flow<List<Order>> =
        dao.getAllOrders().map { list -> list.map { it.toDomain(json) } }

    override suspend fun placeOrder(items: List<CartItem>, total: Double) {
        val snapshots = items.map { OrderItemSnapshot(it.title, it.price, it.quantity) }
        val itemsJson = json.encodeToString(snapshots)
        dao.insert(
            OrderEntity(
                date = System.currentTimeMillis(),
                itemsJson = itemsJson,
                totalPrice = total
            )
        )
    }
}