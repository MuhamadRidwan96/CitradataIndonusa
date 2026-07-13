package com.example.citradataindonusa

import com.example.domain.response.DataRegencies
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse

object LocationDummy {

    fun citySuccess(): RegenciesResponse {
        return RegenciesResponse(
            success = true,
            status = 200,
            message = "Success",
            data = listOf(
                DataRegencies(
                    idCity = "1",
                    idProvince = "11",
                    cityName = "Jakarta Selatan"
                ),
                DataRegencies(
                    idCity = "2",
                    idProvince = "11",
                    cityName = "Jakarta Pusat"
                )
            )
        )
    }

    fun provinceEmpty(): ProvinceResponse {
        return ProvinceResponse(
            success = true,
            status = 200,
            message = "Empty",
            data = emptyList()
        )
    }
}
