package com.example.shopease.domain.model

data class OrderSummary(
    val subtotal: Double,
    val discount: Double,
    val shippingCost: Double,
    val total: Double
)