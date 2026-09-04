package com.example.shopease.presentation.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.usecase.GetProductsUseCase
import com.example.shopease.domain.usecase.cart.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryProductsState(
    val category: String = "",
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val error: String? = null
) {
    val bestSellersThisWeek: List<Product>
        get() = products.sortedByDescending { it.reviewCount }.take(5)
}

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val category: String = checkNotNull(savedStateHandle["category"])

    private val _state = MutableStateFlow(CategoryProductsState(category = category))
    val state: StateFlow<CategoryProductsState> = _state.asStateFlow()

    init {
        getProductsUseCase()
            .onEach { allProducts ->
                val filtered = allProducts.filter { it.category == category }
                _state.value = _state.value.copy(isLoading = false, products = filtered)
            }
            .catch { e ->
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
            .launchIn(viewModelScope)
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product)
        }
    }
}