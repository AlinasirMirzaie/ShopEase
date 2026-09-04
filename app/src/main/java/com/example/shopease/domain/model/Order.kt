package com.example.shopease.domain.model

data class OrderItem(
    val title: String,
    val price: Double,
    val quantity: Int
)

data class Order(
    val id: Int,
    val date: Long,
    val items: List<OrderItem>,
    val totalPrice: Double
)