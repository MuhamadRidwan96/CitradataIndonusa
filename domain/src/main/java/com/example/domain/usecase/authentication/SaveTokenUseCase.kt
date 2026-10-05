package com.example.domain.usecase.authentication

import com.example.domain.repository.SaveTokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repository: SaveTokenRepository) {

    suspend operator fun invoke(userId:String, token: String){
      repository.saveToken(userId,token)
    }
}