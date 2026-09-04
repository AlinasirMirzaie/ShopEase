package com.example.shopease.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.CartItem
import com.example.shopease.domain.model.OrderSummary
import com.example.shopease.domain.model.ShippingAddress
import com.example.shopease.domain.usecase.address.GetSavedAddressUseCase
import com.example.shopease.domain.usecase.address.SaveAddressUseCase
import com.example.shopease.domain.usecase.cart.GetCartItemsUseCase
import com.example.shopease.domain.usecase.order.CalculateOrderSummaryUseCase
import com.example.shopease.domain.usecase.order.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SHIPPING_COST = 15.0
data class CheckoutState(
    val recipientName: String = "",
    val address: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val recipientNameError: String? = null,
    val addressError: String? = null,
    val postalCodeError: String? = null,
    val phoneError: String? = null,
    val isPlacingOrder: Boolean = false,
    val orderPlaced: Boolean = false
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val calculateOrderSummaryUseCase: CalculateOrderSummaryUseCase,
    private val getSavedAddressUseCase: GetSavedAddressUseCase,
    private val saveAddressUseCase: SaveAddressUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase
) : ViewModel() {

    private val cartItems: StateFlow<List<CartItem>> = getCartItemsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orderSummary: StateFlow<OrderSummary> = cartItems
        .map { items -> calculateOrderSummaryUseCase(items, shippingCost = SHIPPING_COST) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            OrderSummary(0.0, 0.0, SHIPPING_COST, SHIPPING_COST)
        )

    private val _state = MutableStateFlow(CheckoutState())
    val state: StateFlow<CheckoutState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val saved = getSavedAddressUseCase().first()
            _state.value = _state.value.copy(
                recipientName = saved.recipientName,
                address = saved.address,
                postalCode = saved.postalCode,
                phone = saved.phone
            )
        }
    }

    fun onRecipientNameChange(v: String) { _state.value = _state.value.copy(recipientName = v, recipientNameError = null) }
    fun onAddressChange(v: String) { _state.value = _state.value.copy(address = v, addressError = null) }
    fun onPostalCodeChange(v: String) { _state.value = _state.value.copy(postalCode = v, postalCodeError = null) }
    fun onPhoneChange(v: String) { _state.value = _state.value.copy(phone = v, phoneError = null) }

    private fun validate(): Boolean {
        val current = _state.value
        var recipientError: String? = null
        var addressError: String? = null
        var postalError: String? = null
        var phoneError: String? = null

        if (current.recipientName.isBlank()) recipientError = "نام گیرنده را وارد کنید"
        if (current.address.isBlank()) addressError = "آدرس را وارد کنید"
        if (current.postalCode.isBlank()) {
            postalError = "کد پستی را وارد کنید"
        } else if (current.postalCode.length != 10) {
            postalError = "کد پستی باید ۱۰ رقم باشد"
        }
        if (current.phone.isBlank()) {
            phoneError = "شماره همراه را وارد کنید"
        } else if (current.phone.length < 10) {
            phoneError = "شماره همراه نامعتبر است"
        }

        _state.value = current.copy(
            recipientNameError = recipientError,
            addressError = addressError,
            postalCodeError = postalError,
            phoneError = phoneError
        )
        return listOf(recipientError, addressError, postalError, phoneError).all { it == null }
    }

    fun placeOrder() {
        if (!validate()) return

        val current = _state.value
        viewModelScope.launch {
            _state.value = current.copy(isPlacingOrder = true)

            saveAddressUseCase(
                ShippingAddress(
                    recipientName = current.recipientName,
                    address = current.address,
                    postalCode = current.postalCode,
                    phone = current.phone
                )
            )

            val items = cartItems.value
            val summary = calculateOrderSummaryUseCase(items, shippingCost = SHIPPING_COST)
            placeOrderUseCase(items, summary.total)

            _state.value = _state.value.copy(isPlacingOrder = false, orderPlaced = true)
        }
    }
}