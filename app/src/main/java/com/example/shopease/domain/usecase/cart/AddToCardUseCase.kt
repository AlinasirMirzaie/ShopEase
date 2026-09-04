package com.example.shopease.domain.usecase.cart

import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(product: Product) = repository.addToCart(product)
}