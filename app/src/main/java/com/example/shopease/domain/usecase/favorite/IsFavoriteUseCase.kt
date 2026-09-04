package com.example.shopease.domain.usecase.favorite

import com.example.shopease.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(productId: Int): Flow<Boolean> = repository.isFavorite(productId)
}