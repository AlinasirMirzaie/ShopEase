package com.example.shopease.domain.usecase.auth

import com.example.shopease.domain.model.User
import com.example.shopease.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        repository.login(email, password)
}