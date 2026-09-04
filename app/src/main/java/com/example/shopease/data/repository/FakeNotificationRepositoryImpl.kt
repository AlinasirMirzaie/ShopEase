package com.example.shopease.data.repository

import com.example.shopease.domain.model.NotificationItem
import com.example.shopease.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeNotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    override fun getNotifications(): Flow<List<NotificationItem>> = flowOf(
        listOf(
            NotificationItem(
                id = 1,
                title = "سفارش شما ارسال شد",
                message = "سفارش شما با موفقیت ثبت و آماده‌ی ارسال است.",
                date = System.currentTimeMillis() - 3_600_000,
                isRead = false
            ),
            NotificationItem(
                id = 2,
                title = "تخفیف ویژه",
                message = "تا پایان هفته، ۲۰٪ تخفیف روی محصولات منتخب.",
                date = System.currentTimeMillis() - 86_400_000,
                isRead = true
            )
        )
    )
}