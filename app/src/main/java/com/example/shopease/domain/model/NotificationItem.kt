package com.example.shopease.domain.model

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val date: Long,
    val isRead: Boolean
)