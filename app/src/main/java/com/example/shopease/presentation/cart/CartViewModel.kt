package com.example.shopease.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.OrderSummary
import com.example.shopease.domain.usecase.cart.GetCartItemsUseCase
import com.example.shopease.domain.usecase.cart.UpdateCartQuantityUseCase
import com.example.shopease.domain.usecase.order.CalculateOrderSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    getCartItemsUseCase: GetCartItemsUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val calculateOrderSummaryUseCase: CalculateOrderSummaryUseCase
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = getCartItemsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orderSummary: StateFlow<OrderSummary> = cartItems
        .map { items -> calculateOrderSummaryUseCase(items) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            OrderSummary(0.0, 0.0, 0.0, 0.0)
        )

    fun increaseQuantity(item: CartItem) {
        viewModelScope.launch { updateCartQuantityUseCase(item.productId, item.quantity + 1) }
    }

    fun decreaseQuantity(item: CartItem) {
        viewModelScope.launch { updateCartQuantityUseCase(item.productId, item.quantity - 1) }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch { updateCartQuantityUseCase(item.productId, 0) }
    }
}