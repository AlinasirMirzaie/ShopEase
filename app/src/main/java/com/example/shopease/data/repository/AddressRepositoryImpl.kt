package com.example.shopease.data.repository

import com.example.shopease.data.local.datastore.UserPreferences
import com.example.shopease.domain.model.ShippingAddress
import com.example.shopease.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
) : AddressRepository {

    override val savedAddress: Flow<ShippingAddress> = combine(
        preferences.shippingRecipient,
        preferences.shippingAddress,
        preferences.shippingPostalCode,
        preferences.shippingPhone
    ) { recipient, address, postalCode, phone ->
        ShippingAddress(
            recipientName = recipient ?: "",
            address = address ?: "",
            postalCode = postalCode ?: "",
            phone = phone ?: ""
        )
    }

    override suspend fun saveAddress(address: ShippingAddress) {
        preferences.saveShippingAddress(
            recipientName = address.recipientName,
            address = address.address,
            postalCode = address.postalCode,
            phone = address.phone
        )
    }
}