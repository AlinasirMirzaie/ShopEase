package com.example.shopease.domain.repository

import com.example.shopease.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun getProductById(id: Int): Product?
    suspend fun refreshProducts()
}