package com.example.core_ui.architecture.state

fun interface Reducer<S> {
    fun reduce(state: S): S
}