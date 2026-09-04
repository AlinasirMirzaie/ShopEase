package com.example.shopease.data.mapper

import com.example.shopease.data.local.entity.ProductEntity
import com.example.shopease.data.remote.dto.ProductDto
import com.example.shopease.domain.model.Product

fun ProductDto.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    imageUrl = image,
    category = category,
    rating = rating?.rate ?: 0f,
    reviewCount = rating?.count ?: 0,
    stock = 100
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    imageUrl = imageUrl,
    category = category,
    rating = rating,
    reviewCount = reviewCount,
    stock = stock
)