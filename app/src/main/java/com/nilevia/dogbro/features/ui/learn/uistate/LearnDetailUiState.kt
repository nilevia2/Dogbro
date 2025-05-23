package com.nilevia.dogbro.features.ui.learn.uistate


sealed interface LearnDetailUiState {
    data class Success(val img: List<String>) : LearnDetailUiState
    object Error : LearnDetailUiState
    object Loading : LearnDetailUiState
}
