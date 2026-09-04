package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.ShippingAddress
import com.example.shopease.domain.usecase.address.GetSavedAddressUseCase
import com.example.shopease.domain.usecase.address.SaveAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyAddressesState(
    val recipientName: String = "",
    val address: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class MyAddressesViewModel @Inject constructor(
    private val getSavedAddressUseCase: GetSavedAddressUseCase,
    private val saveAddressUseCase: SaveAddressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyAddressesState())
    val state: StateFlow<MyAddressesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val saved = getSavedAddressUseCase().first()
            _state.value = _state.value.copy(
                isLoading = false,
                recipientName = saved.recipientName,
                address = saved.address,
                postalCode = saved.postalCode,
                phone = saved.phone
            )
        }
    }

    fun onRecipientNameChange(v: String) { _state.value = _state.value.copy(recipientName = v) }
    fun onAddressChange(v: String) { _state.value = _state.value.copy(address = v) }
    fun onPostalCodeChange(v: String) { _state.value = _state.value.copy(postalCode = v) }
    fun onPhoneChange(v: String) { _state.value = _state.value.copy(phone = v) }

    fun save() {
        val current = _state.value
        viewModelScope.launch {
            _state.value = current.copy(isSaving = true)
            saveAddressUseCase(
                ShippingAddress(
                    recipientName = current.recipientName,
                    address = current.address,
                    postalCode = current.postalCode,
                    phone = current.phone
                )
            )
            _state.value = _state.value.copy(isSaving = false, isSaved = true)
        }
    }
}