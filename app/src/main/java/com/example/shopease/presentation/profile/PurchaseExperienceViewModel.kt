package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.order.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class PurchaseStats(
    val totalOrders: Int = 0,
    val totalSpent: Double = 0.0,
    val averageOrderValue: Double = 0.0
)

@HiltViewModel
class PurchaseExperienceViewModel @Inject constructor(
    getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    val stats: StateFlow<PurchaseStats> = getOrdersUseCase()
        .map { orders ->
            val total = orders.sumOf { it.totalPrice }
            PurchaseStats(
                totalOrders = orders.size,
                totalSpent = total,
                averageOrderValue = if (orders.isNotEmpty()) total / orders.size else 0.0
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PurchaseStats())
}