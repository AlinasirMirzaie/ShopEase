package com.example.shopease.presentation.product

import com.example.shopease.domain.model.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String? = null
) {
    val categories: List<String>
        get() = products.map { it.category }.distinct()

    val categoryImages: Map<String, String>
        get() = products.groupBy { it.category }.mapValues { (_, items) -> items.first().imageUrl }

    val bestSellers: List<Product>
        get() = products.sortedByDescending { it.reviewCount }.take(10)

    val filteredProducts: List<Product>
        get() = products.filter { product ->
            val matchesQuery = product.title.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == null || product.category == selectedCategory
            matchesQuery && matchesCategory
        }
}