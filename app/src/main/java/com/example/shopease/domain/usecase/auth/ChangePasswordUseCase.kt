package com.example.shopease.domain.usecase.auth

import com.example.shopease.domain.repository.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(currentPassword: String, newPassword: String): Result<Unit> =
        repository.changePassword(currentPassword, newPassword)
}