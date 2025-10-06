package com.example.features.presentation.home.screen

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.notification.GetNotificationCountUseCase
import com.example.domain.usecase.notification.ResetNotificationCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    getNotificationCountUseCase: GetNotificationCountUseCase,
    private val resetNotificationCountUseCase: ResetNotificationCountUseCase
) : ViewModel() {

    val count = getNotificationCountUseCase()

    fun reset() = resetNotificationCountUseCase()

}
