package com.example.shopease.presentation.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, emailError = null, generalError = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, passwordError = null, generalError = null)
    }

    private fun validate(): Boolean {
        val current = _state.value
        var emailError: String? = null
        var passwordError: String? = null

        if (current.email.isBlank()) {
            emailError = "ایمیل را وارد کنید"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(current.email).matches()) {
            emailError = "فرمت ایمیل نامعتبر است"
        }

        if (current.password.isBlank()) {
            passwordError = "رمز عبور را وارد کنید"
        } else if (current.password.length < 6) {
            passwordError = "رمز عبور باید حداقل ۶ کاراکتر باشد"
        }

        _state.value = current.copy(emailError = emailError, passwordError = passwordError)
        return emailError == null && passwordError == null
    }

    fun login() {
        if (!validate()) return

        val current = _state.value
        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, generalError = null)
            val result = loginUseCase(current.email, current.password)
            result.onSuccess {
                _state.value = _state.value.copy(isLoading = false, isLoggedIn = true)
            }.onFailure { e ->
                _state.value = _state.value.copy(isLoading = false, generalError = e.message)
            }
        }
    }
}