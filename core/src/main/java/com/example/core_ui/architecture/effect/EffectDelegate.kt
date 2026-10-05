package com.example.core_ui.architecture.effect

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class EffectDelegate <E>{

    private val channel = Channel<E>(Channel.BUFFERED)

    val flow = channel.receiveAsFlow()

    suspend fun send(
        effect:E
    ){
        channel.send(effect)
    }

}