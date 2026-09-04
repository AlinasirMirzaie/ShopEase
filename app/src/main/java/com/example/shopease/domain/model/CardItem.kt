package com.example.shopease.domain.model


data class CartItem(
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int
) {
    val totalPrice: Double get() = price * quantity
}