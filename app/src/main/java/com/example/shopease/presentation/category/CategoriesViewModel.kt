package com.example.shopease.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class CategoriesState(
    val isLoading: Boolean = true,
    val categories: List<String> = emptyList(),
    val categoryImages: Map<String, String> = emptyMap(),
    val error: String? = null
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        getProductsUseCase()
            .onEach { products ->
                val categories = products.map { it.category }.distinct()
                val images = products
                    .groupBy { it.category }
                    .mapValues { (_, items) -> items.first().imageUrl }

                _state.value = _state.value.copy(
                    isLoading = false,
                    categories = categories,
                    categoryImages = images
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
            .launchIn(viewModelScope)
    }
}