package com.example.shopease.domain.usecase

import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? = repository.getProductById(id)
}