package com.example.shopease.data.repository

import com.example.shopease.data.local.dao.ProductDao
import com.example.shopease.data.mapper.toDomain
import com.example.shopease.data.mapper.toEntity
import com.example.shopease.data.remote.api.ProductApi
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> =
        dao.getAllProducts().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getProductById(id: Int): Product? =
        dao.getProductById(id)?.toDomain()

    override suspend fun refreshProducts() {
        val remoteProducts = api.getProducts()
        dao.insertProducts(remoteProducts.map { it.toEntity() })
    }
}