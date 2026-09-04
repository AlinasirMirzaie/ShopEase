package com.example.shopease.presentation.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.auth.SendPasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isSent: Boolean = false
)

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendPasswordResetUseCase: SendPasswordResetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, emailError = null, generalError = null)
    }

    fun sendResetLink() {
        val current = _state.value
        if (current.email.isBlank()) {
            _state.value = current.copy(emailError = "ایمیل را وارد کنید")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(current.email).matches()) {
            _state.value = current.copy(emailError = "فرمت ایمیل نامعتبر است")
            return
        }

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, generalError = null)
            val result = sendPasswordResetUseCase(current.email)
            result.onSuccess {
                _state.value = _state.value.copy(isLoading = false, isSent = true)
            }.onFailure { e ->
                _state.value = _state.value.copy(isLoading = false, generalError = e.message)
            }
        }
    }
}