package com.example.shopease.domain.usecase.cart

import com.example.shopease.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) =
        repository.updateQuantity(productId, quantity)
}