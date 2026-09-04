package com.example.shopease.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.Review
import com.example.shopease.domain.usecase.GetProductByIdUseCase
import com.example.shopease.domain.usecase.GetProductsUseCase
import com.example.shopease.domain.usecase.cart.AddToCartUseCase
import com.example.shopease.domain.usecase.favorite.IsFavoriteUseCase
import com.example.shopease.domain.usecase.favorite.ToggleFavoriteUseCase
import com.example.shopease.domain.usecase.review.GetReviewsForProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ProductDetailState(
    val product: Product? = null,
    val reviews: List<Review> = emptyList(),
    val similarProducts: List<Product> = emptyList(),
    val isFavorite: Boolean = false
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getReviewsForProductUseCase: GetReviewsForProductUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val product = getProductByIdUseCase(productId)
            _state.value = _state.value.copy(product = product)
            if (product != null) {
                getReviewsForProductUseCase(productId, product.reviewCount, product.rating)
                    .collect { reviews -> _state.value = _state.value.copy(reviews = reviews) }
            }
        }

        viewModelScope.launch {
            val allProducts = getProductsUseCase().first()
            val current = getProductByIdUseCase(productId)
            val similar = allProducts.filter { it.category == current?.category && it.id != productId }.take(10)
            _state.value = _state.value.copy(similarProducts = similar)
        }

        viewModelScope.launch {
            isFavoriteUseCase(productId).collect { isFav ->
                _state.value = _state.value.copy(isFavorite = isFav)
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch { _state.value.product?.let { addToCartUseCase(it) } }
    }

    fun toggleFavorite() {
        viewModelScope.launch { _state.value.product?.let { toggleFavoriteUseCase(it) } }
    }
}