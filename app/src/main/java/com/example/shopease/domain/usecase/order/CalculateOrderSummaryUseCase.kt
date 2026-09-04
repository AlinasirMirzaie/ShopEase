package com.example.shopease.domain.usecase.order

import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.OrderSummary
import javax.inject.Inject

class CalculateOrderSummaryUseCase @Inject constructor() {
    operator fun invoke(items: List<CartItem>, shippingCost: Double = 0.0): OrderSummary {
        val subtotal = items.sumOf { it.totalPrice }
        val discount = if (subtotal > 100) subtotal * 0.1 else 0.0
        val total = subtotal - discount + shippingCost
        return OrderSummary(
            subtotal = subtotal,
            discount = discount,
            shippingCost = shippingCost,
            total = total
        )
    }
}