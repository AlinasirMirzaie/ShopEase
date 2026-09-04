package com.example.shopease.data.repository

import android.util.Patterns
import com.example.shopease.data.local.datastore.UserPreferences
import com.example.shopease.domain.model.User
import com.example.shopease.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FakeAuthRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
) : AuthRepository {

    override val isLoggedIn: Flow<Boolean> = preferences.isLoggedIn

    override val currentUser: Flow<User?> =
        combine(preferences.userEmail, preferences.userName) { email, name ->
            if (email != null && name != null) User(email, name) else null
        }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> {
        delay(600)

        if (currentPassword.length < 6) {
            return Result.failure(IllegalArgumentException("رمز عبور فعلی نامعتبر است"))
        }
        if (newPassword.length < 6) {
            return Result.failure(IllegalArgumentException("رمز عبور جدید باید حداقل ۶ کاراکتر باشد"))
        }
        return Result.success(Unit)
    }

    override suspend fun login(email: String, password: String): Result<User> {
        delay(600)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("فرمت ایمیل نامعتبر است"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("رمز عبور باید حداقل ۶ کاراکتر باشد"))
        }
        val displayName = email.substringBefore("@")
        preferences.saveSession(email, displayName)
        return Result.success(User(email, displayName))
    }

    override suspend fun register(email: String, password: String, name: String): Result<User> {
        delay(600)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("فرمت ایمیل نامعتبر است"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("رمز عبور باید حداقل ۶ کاراکتر باشد"))
        }
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("نام نمی‌تواند خالی باشد"))
        }
        preferences.saveSession(email, name)
        return Result.success(User(email, name))
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> {
        delay(600)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("فرمت ایمیل نامعتبر است"))
        }
        return Result.success(Unit)
    }

    override suspend fun logout() {
        preferences.clearSession()
    }
}