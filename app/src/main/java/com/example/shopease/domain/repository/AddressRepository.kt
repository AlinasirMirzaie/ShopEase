package com.example.shopease.domain.repository

import com.example.shopease.domain.model.ShippingAddress
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    val savedAddress: Flow<ShippingAddress>
    suspend fun saveAddress(address: ShippingAddress)
}