package com.example.shopease.domain.usecase.order

import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.repository.CartRepository
import com.example.shopease.domain.repository.OrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(items: List<CartItem>, total: Double) {
        orderRepository.placeOrder(items, total)
        cartRepository.clearCart()
    }
}