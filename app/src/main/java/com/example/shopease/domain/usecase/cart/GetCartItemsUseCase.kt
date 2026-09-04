package com.example.shopease.domain.usecase.cart

import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> = repository.getCartItems()
}