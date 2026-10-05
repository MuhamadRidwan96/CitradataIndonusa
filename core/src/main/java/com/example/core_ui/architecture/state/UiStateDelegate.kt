package com.example.core_ui.architecture.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UiStateDelegate <S>(
    initial:S
){

    private val _state = MutableStateFlow(initial)

    val state : StateFlow<S> = _state.asStateFlow()

    val value : S
        get() = _state.value

    fun update(
        transform : (S) -> S
    ){
        _state.update(transform)
    }
}