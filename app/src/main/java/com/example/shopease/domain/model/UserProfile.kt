package com.example.shopease.domain.model

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val photoUri: String?,
    val birthDate: String?,
    val gender: String?
)