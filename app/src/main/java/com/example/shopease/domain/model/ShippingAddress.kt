package com.example.shopease.domain.model

data class ShippingAddress(
    val recipientName: String = "",
    val address: String = "",
    val postalCode: String = "",
    val phone: String = ""
)