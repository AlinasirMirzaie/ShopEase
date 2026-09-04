package com.example.shopease.domain.repository

import com.example.shopease.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<List<NotificationItem>>
}