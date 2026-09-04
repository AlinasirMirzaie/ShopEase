package com.example.shopease.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopease.data.local.dao.CartDao
import com.example.shopease.data.local.dao.FavoriteDao
import com.example.shopease.data.local.dao.OrderDao
import com.example.shopease.data.local.dao.ProductDao
import com.example.shopease.data.local.entity.CartItemEntity
import com.example.shopease.data.local.entity.FavoriteEntity
import com.example.shopease.data.local.entity.OrderEntity
import com.example.shopease.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, CartItemEntity::class, OrderEntity::class, FavoriteEntity::class],
    version = 5,
    exportSchema = false
)
abstract class ShopEaseDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun favoriteDao(): FavoriteDao
}