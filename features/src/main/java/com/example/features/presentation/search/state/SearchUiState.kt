package com.example.features.presentation.search.state

import androidx.paging.compose.LazyPagingItems
import com.example.domain.response.RecordData

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val data: LazyPagingItems<RecordData>? ):SearchUiState()
    data class Error(val message:String): SearchUiState()
}


sealed class LocationEvent(){
    data class Error(val message : String) : LocationEvent()
    data object ProvinceLoaded : LocationEvent()
    data object CityLoaded : LocationEvent()
}