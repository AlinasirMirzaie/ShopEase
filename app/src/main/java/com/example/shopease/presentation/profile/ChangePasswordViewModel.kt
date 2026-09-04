package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.auth.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChangePasswordState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val currentPasswordError: String? = null,
    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> = _state.asStateFlow()

    fun onCurrentPasswordChange(v: String) { _state.value = _state.value.copy(currentPassword = v, currentPasswordError = null, generalError = null) }
    fun onNewPasswordChange(v: String) { _state.value = _state.value.copy(newPassword = v, newPasswordError = null, generalError = null) }
    fun onConfirmPasswordChange(v: String) { _state.value = _state.value.copy(confirmPassword = v, confirmPasswordError = null, generalError = null) }

    private fun validate(): Boolean {
        val current = _state.value
        var currentError: String? = null
        var newError: String? = null
        var confirmError: String? = null

        if (current.currentPassword.isBlank()) currentError = "رمز عبور فعلی را وارد کنید"
        if (current.newPassword.isBlank()) {
            newError = "رمز عبور جدید را وارد کنید"
        } else if (current.newPassword.length < 6) {
            newError = "رمز عبور جدید باید حداقل ۶ کاراکتر باشد"
        }
        if (current.confirmPassword != current.newPassword) {
            confirmError = "رمز عبور جدید و تکرار آن یکسان نیستند"
        }

        _state.value = current.copy(
            currentPasswordError = currentError,
            newPasswordError = newError,
            confirmPasswordError = confirmError
        )
        return listOf(currentError, newError, confirmError).all { it == null }
    }

    fun submit() {
        if (!validate()) return
        val current = _state.value
        viewModelScope.launch {
            _state.value = current.copy(isLoading = true, generalError = null)
            val result = changePasswordUseCase(current.currentPassword, current.newPassword)
            result.onSuccess {
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            }.onFailure { e ->
                _state.value = _state.value.copy(isLoading = false, generalError = e.message)
            }
        }
    }
}