package com.example.shopease.domain.usecase


import com.example.shopease.domain.repository.ProductRepository
import javax.inject.Inject

class RefreshProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.refreshProducts()
}