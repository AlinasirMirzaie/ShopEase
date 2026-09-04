package com.example.shopease.domain.usecase

import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.getProducts()
}