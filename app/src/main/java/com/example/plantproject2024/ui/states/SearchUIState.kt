package com.example.plantproject2024.ui.states

sealed interface SearchUIState {
    //Initial state
    object Initial : SearchUIState
    //Loading state
    object Loading : SearchUIState

    //Success state, with output content property
    data class Success(val outputContent: String) : SearchUIState

    //Somehthing went wrong, send error message
    data class Error(val errorMessage: String) : SearchUIState


}