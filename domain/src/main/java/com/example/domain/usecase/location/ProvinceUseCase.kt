package com.example.domain.usecase.location

import com.example.domain.di.IoDispatcher
import com.example.domain.model.Province
import com.example.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProvinceUseCase @Inject constructor(
    private val repository: LocationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(provinceModel: Province): Result<List<Province>>{
        return withContext(dispatcher){
            repository.province(provinceModel)
        }
    }
}

