package com.example.shopease.domain.usecase.favorite

import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(product: Product) = repository.toggleFavorite(product)
}