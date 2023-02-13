package com.kk.presentation.host.creategame

import com.kk.presentation.baseMVI.UiEffect
import com.kk.presentation.baseMVI.UiEvent
import com.kk.presentation.baseMVI.UiState


class CreateRoomContract {
    // Events that user performed
    sealed class Event : UiEvent {
        object OnCreateRoom : Event()
        object OnChangePlayers: Event()
        object OnChangePoints: Event()
        object OnChangeTime: Event()
    }

    // UI View States
    data class State(val createRoomState : CreateRoomState): UiState

    // View State
    sealed class CreateRoomState {
        object Idle : CreateRoomState()
        data class Error(val message: String) : CreateRoomState()
        data class Success(val value: String) : CreateRoomState()
    }

    // Side Effect
    sealed class Effect : UiEffect { object Navigate : Effect() }
}