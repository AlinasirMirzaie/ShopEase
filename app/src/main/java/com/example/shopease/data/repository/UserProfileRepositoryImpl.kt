package com.example.shopease.data.repository

import com.example.shopease.data.local.datastore.UserPreferences
import com.example.shopease.domain.model.UserProfile
import com.example.shopease.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
) : UserProfileRepository {

    override val profile: Flow<UserProfile> = combine(
        preferences.userName,
        preferences.userEmail,
        preferences.userPhone,
        preferences.userPhotoUri,
        preferences.userBirthDate,
        preferences.userGender
    ) { values ->
        UserProfile(
            name = values[0] ?: "",
            email = values[1] ?: "",
            phone = values[2] ?: "",
            photoUri = values[3],
            birthDate = values[4],
            gender = values[5]
        )
    }

    override suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        photoUri: String?,
        birthDate: String?,
        gender: String?
    ) {
        preferences.updateProfile(name, email, phone, photoUri, birthDate, gender)
    }
}