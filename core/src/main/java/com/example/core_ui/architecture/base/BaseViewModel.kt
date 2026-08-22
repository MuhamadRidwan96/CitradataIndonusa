package com.example.core_ui.architecture.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.effect.EffectDelegate
import com.example.core_ui.architecture.state.UiStateDelegate
import kotlinx.coroutines.launch

abstract class  BaseViewModel<
        STATE : BaseUiState,
        EVENT : BaseUiEvent>
    (initialState: STATE) : ViewModel() {

    private val stateDelegate = UiStateDelegate(initialState)

    protected val effectDelegate = EffectDelegate<EVENT>()

    val uiState = stateDelegate.state

    val uiEvent = effectDelegate.flow

    /**
     * State terbaru secara synchronous.
     *
     * Hanya dapat digunakan oleh ViewModel turunan.
     */
    protected val currentState
        get() = stateDelegate.value

    /**
     * Mengubah state.
     */
   protected fun reduce(
        transform: STATE.() -> STATE
    ) {
        stateDelegate.update { state ->
            state.transform()
        }
    }

    /**
     * Mengirim one-shot event.
     */

    protected fun sendEvent(
        event: EVENT
    ) {
        viewModelScope.launch {
            effectDelegate.send(event)
        }
    }
}