package com.example.core_ui.architecture.action

import com.example.core_ui.architecture.base.BaseUiAction

interface ActionHandler<A : BaseUiAction> {
    fun action(action: A)
}