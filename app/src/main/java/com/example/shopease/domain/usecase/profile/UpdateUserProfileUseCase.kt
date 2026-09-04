package com.example.shopease.domain.usecase.profile

import com.example.shopease.domain.repository.UserProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        phone: String,
        photoUri: String?,
        birthDate: String?,
        gender: String?
    ) = repository.updateProfile(name, email, phone, photoUri, birthDate, gender)
}