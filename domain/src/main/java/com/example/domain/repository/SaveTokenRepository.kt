package com.example.domain.repository

interface SaveTokenRepository {
   suspend fun saveToken(userId:String,token:String)
}