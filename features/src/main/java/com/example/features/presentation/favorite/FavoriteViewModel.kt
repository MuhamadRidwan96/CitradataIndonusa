package com.example.features.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.utils.Constant
import com.example.domain.model.FavoriteProject
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.features.presentation.home.screen.DataEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _dataEvent = Channel<DataEvent>(Channel.BUFFERED)
    val dataEvent = _dataEvent.receiveAsFlow()

    private val _favorites = MutableStateFlow<List<FavoriteProject>>(emptyList())
    val favorites: StateFlow<List<FavoriteProject>> = _favorites

    init {
        observeFavorite()
    }

    private fun observeFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavoriteUseCase().collect { fav ->
                _favorites.value = fav
            }
        }
    }

    fun toggleFavorite(project: FavoriteProjectEntity) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val favorite = _favorites.value.any { it.idProject == project.idProject }
                if (favorite) deleteFavoriteUseCase(project.idProject)
                _dataEvent.send(DataEvent.ShowSnackBar("Favorite Berhasil dihapus!"))
            }
        } catch (e: Exception) {
            DataEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR)
        }
    }
}