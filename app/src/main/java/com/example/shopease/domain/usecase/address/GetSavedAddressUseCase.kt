package com.example.shopease.domain.usecase.address

import com.example.shopease.domain.model.ShippingAddress
import com.example.shopease.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(): Flow<ShippingAddress> = repository.savedAddress
}