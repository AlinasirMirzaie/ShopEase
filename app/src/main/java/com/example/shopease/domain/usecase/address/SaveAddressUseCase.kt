package com.example.shopease.domain.usecase.address

import com.example.shopease.domain.model.ShippingAddress
import com.example.shopease.domain.repository.AddressRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: ShippingAddress) = repository.saveAddress(address)
}