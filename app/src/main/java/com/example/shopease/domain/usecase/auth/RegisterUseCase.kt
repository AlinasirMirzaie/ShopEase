package com.example.shopease.domain.usecase.auth

import com.example.shopease.domain.model.User
import com.example.shopease.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<User> =
        repository.register(email, password, name)
}