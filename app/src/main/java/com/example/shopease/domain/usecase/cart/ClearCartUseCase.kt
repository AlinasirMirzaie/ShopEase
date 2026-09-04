package com.example.shopease.domain.usecase.cart

import com.example.shopease.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke() = repository.clearCart()
}