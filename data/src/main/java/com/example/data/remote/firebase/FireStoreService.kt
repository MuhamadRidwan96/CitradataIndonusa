package com.example.data.remote.firebase

import com.example.domain.model.UserModels
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreService @Inject constructor(){

    private val db = FirebaseFirestore.getInstance()

    suspend fun saveUserToCloud(user: UserModels) {
        val userMap = mapOf(
            "name" to user.name,
            "email" to user.email,
            "photoUrl" to user.photoUrl
        )

        db.collection("users")
            .document(user.email)
            .set(userMap)
            .await() // gunakan kotlinx-coroutines-play-services
    }

}