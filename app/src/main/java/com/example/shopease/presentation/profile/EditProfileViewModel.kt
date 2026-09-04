package com.example.shopease.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.profile.GetUserProfileUseCase
import com.example.shopease.domain.usecase.profile.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

data class EditProfileState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val photoUri: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val profile = getUserProfileUseCase().first()
            _state.value = _state.value.copy(
                isLoading = false,
                name = profile.name,
                email = profile.email,
                phone = profile.phone,
                photoUri = profile.photoUri,
                birthDate = profile.birthDate,
                gender = profile.gender
            )
        }
    }

    fun onNameChange(v: String) { _state.value = _state.value.copy(name = v, nameError = null) }
    fun onEmailChange(v: String) { _state.value = _state.value.copy(email = v, emailError = null) }
    fun onPhoneChange(v: String) { _state.value = _state.value.copy(phone = v) }
    fun onBirthDateChange(v: String) { _state.value = _state.value.copy(birthDate = v) }
    fun onGenderChange(v: String) { _state.value = _state.value.copy(gender = v) }

    fun onPhotoPicked(uri: Uri, context: Context) {
        viewModelScope.launch {
            val savedPath = withContext(Dispatchers.IO) {
                copyImageToInternalStorage(context, uri)
            }
            _state.value = _state.value.copy(photoUri = savedPath)
        }
    }

    private fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, "profile_photo.jpg")
            FileOutputStream(file).use { output ->
                inputStream.copyTo(output)
            }
            inputStream.close()
            Uri.fromFile(file).toString()
        } catch (e: Exception) {
            null
        }
    }

    fun save() {
        val current = _state.value
        var nameError: String? = null
        var emailError: String? = null

        if (current.name.isBlank()) nameError = "نام را وارد کنید"
        if (current.email.isBlank()) emailError = "ایمیل را وارد کنید"

        if (nameError != null || emailError != null) {
            _state.value = current.copy(nameError = nameError, emailError = emailError)
            return
        }

        viewModelScope.launch {
            _state.value = current.copy(isSaving = true)
            updateUserProfileUseCase(
                name = current.name,
                email = current.email,
                phone = current.phone,
                photoUri = current.photoUri,
                birthDate = current.birthDate,
                gender = current.gender
            )
            _state.value = _state.value.copy(isSaving = false, isSaved = true)
        }
    }
}