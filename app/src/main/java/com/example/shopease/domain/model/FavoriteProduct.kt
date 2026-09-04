package com.example.shopease.domain.model

data class FavoriteProduct(
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String
)