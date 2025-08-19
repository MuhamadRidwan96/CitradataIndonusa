package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.FilterDataModel
import com.example.domain.response.RecordData
import com.example.domain.usecase.data.FilteredUseCase
import com.example.features.presentation.search.state.ProjectFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filterDataUseCase: FilteredUseCase
): ViewModel(){

    private val _searchState = MutableStateFlow(ProjectFilterState(isLoading = true))
    val searchState = _searchState.asStateFlow()
    private val _filterData = MutableStateFlow<FilterDataModel?>(null)

    //Generic update function menggunakan lambda
    fun updateFilterState(update: ProjectFilterState.()-> ProjectFilterState){
        _searchState.update { it.update() }
    }



    fun updateStatus(statusCategory:String){ _searchState.update { it.copy(idProjectStatusCategory = statusCategory) } }
    fun updatePpr(isChecked: Boolean){_searchState.update { it.copy(withPpr = if (isChecked) "ppr" else "") }}
    fun updateDeveloper(developer: Int?){_searchState.update { it.copy(idDeveloper = developer) }}
    fun updateConsultant(consultant: String){_searchState.update { it.copy(idConsultant = consultant) }}
    fun updateContractor(contractor: String){_searchState.update { it.copy(idContractor = contractor) }}
    fun updateCategoryBuilding(buildingCategory:Int?){_searchState.update { it.copy(idBuildingCategory = buildingCategory) } }
    fun updateProvince(province:String){_searchState.update { it.copy(idProvince = province) } }
    fun updateCity(city:String?){_searchState.update { it.copy(idCity = city, idProvince = "") }}
    fun updateSector(sectorCategory:String){_searchState.update { it.copy(idSectorCategory = sectorCategory) } }
    fun onQueryChange(newQuery: String){_searchState.value = _searchState.value.copy(query = newQuery)}
    fun setLoading(isLoading: Boolean){_searchState.value = _searchState.value.copy(isLoading = isLoading)}


    @OptIn(ExperimentalCoroutinesApi::class)
    val dataPagingFlow : Flow<PagingData<RecordData>> = _filterData
        .flatMapLatest { filter ->
            filterDataUseCase(

                filterData = filter
            )
        }
        .cachedIn(viewModelScope)

    fun setFilter(filterData: FilterDataModel){
        viewModelScope.launch {
            _filterData.value = filterData
        }
    }
}
