package com.example.shopease.di

import android.content.Context
import androidx.room.Room
import com.example.shopease.data.local.ShopEaseDatabase
import com.example.shopease.data.local.dao.CartDao
import com.example.shopease.data.local.dao.FavoriteDao
import com.example.shopease.data.local.dao.OrderDao
import com.example.shopease.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteDao(database: ShopEaseDatabase): FavoriteDao = database.favoriteDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShopEaseDatabase =
        Room.databaseBuilder(context, ShopEaseDatabase::class.java, "shopease_db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    @Singleton
    fun provideProductDao(database: ShopEaseDatabase): ProductDao = database.productDao()

    @Provides
    @Singleton
    fun provideCartDao(database: ShopEaseDatabase): CartDao = database.cartDao()

    @Provides
    @Singleton
    fun provideOrderDao(database: ShopEaseDatabase): OrderDao = database.orderDao()
}