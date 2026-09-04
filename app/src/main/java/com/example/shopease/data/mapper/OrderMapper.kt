package com.example.shopease.data.mapper

import com.example.shopease.data.local.entity.OrderEntity
import com.example.shopease.data.remote.dto.OrderItemSnapshot
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderItem
import kotlinx.serialization.json.Json

fun OrderEntity.toDomain(json: Json): Order {
    val snapshots = json.decodeFromString<List<OrderItemSnapshot>>(itemsJson)
    return Order(
        id = id,
        date = date,
        items = snapshots.map { OrderItem(it.title, it.price, it.quantity) },
        totalPrice = totalPrice
    )
}