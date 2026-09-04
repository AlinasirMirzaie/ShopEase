package com.example.shopease.presentation.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onNameChange(v: String) { _state.value = _state.value.copy(name = v, nameError = null, generalError = null) }
    fun onEmailChange(v: String) { _state.value = _state.value.copy(email = v, emailError = null, generalError = null) }
    fun onPasswordChange(v: String) { _state.value = _state.value.copy(password = v, passwordError = null, generalError = null) }
    fun onConfirmPasswordChange(v: String) { _state.value = _state.value.copy(confirmPassword = v, confirmPasswordError = null, generalError = null) }

    private fun validate(): Boolean {
        val current = _state.value
        var nameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        if (current.name.isBlank()) {
            nameError = "نام را وارد کنید"
        }
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
        if (current.confirmPassword != current.password) {
            confirmPasswordError = "رمز عبور و تکرار آن یکسان نیستند"
        }

        _state.value = current.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )
        return listOf(nameError, emailError, passwordError, confirmPasswordError).all { it == null }
    }

    fun register() {
        if (!validate()) return

        val current = _state.value
        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, generalError = null)
            val result = registerUseCase(current.email, current.password, current.name)
            result.onSuccess {
                _state.value = _state.value.copy(isLoading = false, isRegistered = true)
            }.onFailure { e ->
                _state.value = _state.value.copy(isLoading = false, generalError = e.message)
            }
        }
    }
}