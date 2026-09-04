package com.example.shopease.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "shopease_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LAST_CATEGORY = stringPreferencesKey("last_category")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PHONE = stringPreferencesKey("user_phone")
        val USER_PHOTO_URI = stringPreferencesKey("user_photo_uri")
        val USER_BIRTH_DATE = stringPreferencesKey("user_birth_date")
        val USER_GENDER = stringPreferencesKey("user_gender")

        val SHIPPING_RECIPIENT = stringPreferencesKey("shipping_recipient")
        val SHIPPING_ADDRESS = stringPreferencesKey("shipping_address")
        val SHIPPING_POSTAL_CODE = stringPreferencesKey("shipping_postal_code")
        val SHIPPING_PHONE = stringPreferencesKey("shipping_phone")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[Keys.DARK_MODE] ?: false }
    val lastCategory: Flow<String?> = context.dataStore.data.map { it[Keys.LAST_CATEGORY] }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[Keys.IS_LOGGED_IN] ?: false }
    val userEmail: Flow<String?> = context.dataStore.data.map { it[Keys.USER_EMAIL] }
    val userName: Flow<String?> = context.dataStore.data.map { it[Keys.USER_NAME] }
    val userPhone: Flow<String?> = context.dataStore.data.map { it[Keys.USER_PHONE] }
    val userPhotoUri: Flow<String?> = context.dataStore.data.map { it[Keys.USER_PHOTO_URI] }
    val userBirthDate: Flow<String?> = context.dataStore.data.map { it[Keys.USER_BIRTH_DATE] }
    val userGender: Flow<String?> = context.dataStore.data.map { it[Keys.USER_GENDER] }

    val shippingRecipient: Flow<String?> = context.dataStore.data.map { it[Keys.SHIPPING_RECIPIENT] }
    val shippingAddress: Flow<String?> = context.dataStore.data.map { it[Keys.SHIPPING_ADDRESS] }
    val shippingPostalCode: Flow<String?> = context.dataStore.data.map { it[Keys.SHIPPING_POSTAL_CODE] }
    val shippingPhone: Flow<String?> = context.dataStore.data.map { it[Keys.SHIPPING_PHONE] }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setLastCategory(category: String) {
        context.dataStore.edit { it[Keys.LAST_CATEGORY] = category }
    }

    suspend fun saveSession(email: String, name: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = true
            prefs[Keys.USER_EMAIL] = email
            prefs[Keys.USER_NAME] = name
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = false
            prefs.remove(Keys.USER_EMAIL)
            prefs.remove(Keys.USER_NAME)
        }
    }

    suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        photoUri: String?,
        birthDate: String?,
        gender: String?
    ) {
        context.dataStore.edit { prefs ->
            prefs[Keys.USER_NAME] = name
            prefs[Keys.USER_EMAIL] = email
            prefs[Keys.USER_PHONE] = phone
            photoUri?.let { prefs[Keys.USER_PHOTO_URI] = it }
            birthDate?.let { prefs[Keys.USER_BIRTH_DATE] = it }
            gender?.let { prefs[Keys.USER_GENDER] = it }
        }
    }

    suspend fun saveShippingAddress(
        recipientName: String,
        address: String,
        postalCode: String,
        phone: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SHIPPING_RECIPIENT] = recipientName
            prefs[Keys.SHIPPING_ADDRESS] = address
            prefs[Keys.SHIPPING_POSTAL_CODE] = postalCode
            prefs[Keys.SHIPPING_PHONE] = phone
        }
    }
}