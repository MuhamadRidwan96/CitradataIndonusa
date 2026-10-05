package com.example.features.presentation.favorite

import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.data.utils.Constant
import com.example.domain.di.IoDispatcher
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.features.presentation.favorite.state.FavoriteUiAction
import com.example.features.presentation.favorite.state.FavoriteUiEvent
import com.example.features.presentation.favorite.state.FavoriteUiState
import com.example.features.presentation.favorite.utils.toUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<
        FavoriteUiState,
        FavoriteUiEvent
        >(initialState = FavoriteUiState()), ActionHandler<FavoriteUiAction> {

    init {
        observeFavorite()
    }

    override fun action(action: FavoriteUiAction) {
        when (action) {
            is FavoriteUiAction.RemoveFavorite -> {
                removeFavorite(action.projectId)
            }
        }
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            getAllFavoriteUseCase().collect { favorites ->
                reduce {
                    copy(
                        favorite = favorites
                            .map{it.toUiItem()}
                            .toImmutableList()
                    )
                }
            }
        }
    }

    private fun removeFavorite(idProject: Int) {

        viewModelScope.launch(dispatcher) {
            try {
                deleteFavoriteUseCase(idProject)
                sendEvent(
                    FavoriteUiEvent.ShowSnackBar("Favorite has been deleted!")
                )
            } catch (e: Exception) {
                sendEvent(
                    FavoriteUiEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR)
                )
            }
        }

    }
}