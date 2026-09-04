package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.UserProfile
import com.example.shopease.domain.usecase.profile.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    val profile: StateFlow<UserProfile?> = getUserProfileUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}