package com.example.shopease.data.mapper


import com.example.shopease.data.local.entity.CartItemEntity
import com.example.shopease.domain.model.CartItem

fun CartItemEntity.toDomain(): CartItem = CartItem(
    productId = productId,
    title = title,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity
)