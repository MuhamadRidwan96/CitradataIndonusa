package com.example.domain.usecase.notification

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotificationUseCase @Inject constructor( private val repository: NotificationRepository) {

    operator fun invoke() : Flow<List<NotificationModel>>{
        return repository.getAllNotifications()
    }
}

class DeleteNotificationUseCase @Inject constructor( private val repository: NotificationRepository){
    suspend operator fun invoke(id:Int){
        return repository.delete(id)
    }
}