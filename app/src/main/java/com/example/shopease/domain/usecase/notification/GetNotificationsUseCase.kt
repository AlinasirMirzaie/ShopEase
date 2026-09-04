package com.example.shopease.domain.usecase.notification

import com.example.shopease.domain.model.NotificationItem
import com.example.shopease.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<List<NotificationItem>> = repository.getNotifications()
}