package com.example.shopease.domain.usecase.auth

import com.example.shopease.domain.repository.AuthRepository
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        repository.sendPasswordReset(email)
}