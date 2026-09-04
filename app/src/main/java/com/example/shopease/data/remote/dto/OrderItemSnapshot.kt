package com.example.shopease.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemSnapshot(
    val title: String,
    val price: Double,
    val quantity: Int
)