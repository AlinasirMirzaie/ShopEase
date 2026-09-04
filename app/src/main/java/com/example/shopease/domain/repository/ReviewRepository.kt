package com.example.shopease.domain.repository

import com.example.shopease.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getReviewsForProduct(productId: Int, reviewCount: Int, rating: Float): Flow<List<Review>>
}