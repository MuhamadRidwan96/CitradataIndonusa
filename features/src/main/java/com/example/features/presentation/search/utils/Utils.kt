package com.example.features.presentation.search.utils

fun String?.hasValue(): Boolean = !this.isNullOrBlank()
fun Int?.hasValueInt(): Boolean = this != null
