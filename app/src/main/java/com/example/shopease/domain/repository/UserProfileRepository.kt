package com.example.shopease.domain.repository

import com.example.shopease.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    val profile: Flow<UserProfile>

    suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        photoUri: String?,
        birthDate: String?,
        gender: String?
    )
}