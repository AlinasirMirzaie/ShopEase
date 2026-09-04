package com.example.shopease.domain.usecase.order

import com.example.shopease.domain.model.Order
import com.example.shopease.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<List<Order>> = repository.getOrders()
}