// domain/model/Review.kt
package com.example.shopease.domain.model

data class Review(
    val id: Int,
    val userName: String,
    val rating: Float,
    val comment: String,
    val date: Long
)