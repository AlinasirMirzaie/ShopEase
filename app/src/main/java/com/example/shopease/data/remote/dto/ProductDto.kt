package com.example.shopease.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val image: String,
    val rating: RatingDto? = null
)

@Serializable
data class RatingDto(
    val rate: Float = 0f,
    val count: Int = 0
)