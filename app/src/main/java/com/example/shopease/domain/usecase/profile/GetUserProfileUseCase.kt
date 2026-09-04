package com.example.shopease.domain.usecase.profile

import com.example.shopease.domain.model.UserProfile
import com.example.shopease.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<UserProfile> = repository.profile
}