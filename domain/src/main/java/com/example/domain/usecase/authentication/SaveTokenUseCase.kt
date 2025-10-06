package com.example.domain.usecase.authentication

import com.example.common.Result
import com.example.domain.repository.SaveTokenRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repository: SaveTokenRepository) {

    operator fun invoke(userId:String, token: String) : Flow<Result<ResponseBody>>{
        return repository.saveToken(userId,token)
    }
}