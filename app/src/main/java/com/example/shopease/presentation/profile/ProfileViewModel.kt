package com.example.shopease.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.UserProfile
import com.example.shopease.domain.usecase.auth.LogoutUseCase
import com.example.shopease.domain.usecase.profile.GetUserProfileUseCase
import com.example.shopease.domain.usecase.profile.UpdateUserProfileUseCase
import com.example.shopease.presentation.utils.ImageStorageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val profile: StateFlow<UserProfile?> = getUserProfileUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut

    fun changePhoto(uri: Uri, context: Context) {
        viewModelScope.launch {
            val savedPath = withContext(Dispatchers.IO) {
                ImageStorageUtils.copyImageToInternalStorage(context, uri, "profile_photo.jpg")
            } ?: return@launch

            val current = getUserProfileUseCase().first()
            updateUserProfileUseCase(
                name = current.name,
                email = current.email,
                phone = current.phone,
                photoUri = savedPath,
                birthDate = current.birthDate,
                gender = current.gender
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _loggedOut.value = true
        }
    }
}