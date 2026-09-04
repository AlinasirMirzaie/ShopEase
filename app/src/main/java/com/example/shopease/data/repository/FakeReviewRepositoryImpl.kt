package com.example.shopease.data.repository

import com.example.shopease.domain.model.Review
import com.example.shopease.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.random.Random

class FakeReviewRepositoryImpl @Inject constructor() : ReviewRepository {

    private val sampleNames = listOf("علی", "سارا", "محمد", "زهرا", "امیر", "نگین")
    private val sampleComments = listOf(
        "کیفیت خیلی خوبی داشت، راضی بودم.",
        "نسبت به قیمتش عالیه.",
        "بسته‌بندی مناسب بود ولی ارسال کمی طول کشید.",
        "دقیقاً همون چیزی بود که توی عکس دیدم.",
        "کیفیت متوسط بود، انتظار بیشتری داشتم."
    )

    override fun getReviewsForProduct(
        productId: Int,
        reviewCount: Int,
        rating: Float
    ): Flow<List<Review>> {
        val count = reviewCount.coerceIn(0, 5)
        val reviews = (1..count).map { index ->
            Review(
                id = productId * 100 + index,
                userName = sampleNames[Random(productId + index).nextInt(sampleNames.size)],
                rating = rating,
                comment = sampleComments[Random(productId + index).nextInt(sampleComments.size)],
                date = System.currentTimeMillis() - (index * 86_400_000L)
            )
        }
        return flowOf(reviews)
    }
}