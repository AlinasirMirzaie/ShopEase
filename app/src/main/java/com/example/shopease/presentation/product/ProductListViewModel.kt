package com.example.shopease.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.usecase.GetProductsUseCase
import com.example.shopease.domain.usecase.RefreshProductsUseCase
import com.example.shopease.domain.usecase.cart.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    init {
        observeProducts()
        refresh()
    }

    private fun observeProducts() {
        getProductsUseCase()
            .onEach { products ->
                _state.value = _state.value.copy(products = products)
            }
            .catch { e ->
                _state.value = _state.value.copy(error = e.message)
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                refreshProductsUseCase()
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: IOException) {
                _state.value = _state.value.copy(isLoading = false, error = "خطا در اتصال به اینترنت")
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = "خطای غیرمنتظره: ${e.message}")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun onCategorySelect(category: String?) {
        _state.value = _state.value.copy(selectedCategory = category)
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product)
        }
    }
}