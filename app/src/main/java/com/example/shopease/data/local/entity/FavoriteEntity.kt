package com.example.shopease.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String
)