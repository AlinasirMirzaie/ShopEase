package com.example.shopease.di

import com.example.shopease.data.repository.AddressRepositoryImpl
import com.example.shopease.data.repository.CartRepositoryImpl
import com.example.shopease.data.repository.FakeAuthRepositoryImpl
import com.example.shopease.data.repository.FakeNotificationRepositoryImpl
import com.example.shopease.data.repository.FakeReviewRepositoryImpl
import com.example.shopease.data.repository.FavoriteRepositoryImpl
import com.example.shopease.data.repository.OrderRepositoryImpl
import com.example.shopease.data.repository.ProductRepositoryImpl
import com.example.shopease.data.repository.UserProfileRepositoryImpl
import com.example.shopease.domain.repository.AddressRepository
import com.example.shopease.domain.repository.AuthRepository
import com.example.shopease.domain.repository.CartRepository
import com.example.shopease.domain.repository.FavoriteRepository
import com.example.shopease.domain.repository.NotificationRepository
import com.example.shopease.domain.repository.OrderRepository
import com.example.shopease.domain.repository.ProductRepository
import com.example.shopease.domain.repository.ReviewRepository
import com.example.shopease.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindReviewRepository(impl: FakeReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindAddressRepository(impl: AddressRepositoryImpl): AddressRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: FakeAuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: FakeNotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}