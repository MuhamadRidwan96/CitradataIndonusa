package com.example.features.presentation.search.utils

fun String?.hasValue(): Boolean = !this.isNullOrBlank()
fun Int?.hasValueInt(): Boolean = this != null

/*
fun <T : Any> LazyPagingItems<T>.getError(): Throwable? {
    val state = loadState
    return when {
        state.refresh is LoadState.Error -> (state.refresh as LoadState.Error).error
        state.append is LoadState.Error -> (state.append as LoadState.Error).error
        state.prepend is LoadState.Error -> (state.prepend as LoadState.Error).error
        else -> null
    }
}*/
