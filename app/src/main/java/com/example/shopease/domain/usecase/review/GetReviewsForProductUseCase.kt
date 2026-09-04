package com.example.shopease.domain.usecase.review

import com.example.shopease.domain.model.Review
import com.example.shopease.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsForProductUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    operator fun invoke(productId: Int, reviewCount: Int, rating: Float): Flow<List<Review>> =
        repository.getReviewsForProduct(productId, reviewCount, rating)
}