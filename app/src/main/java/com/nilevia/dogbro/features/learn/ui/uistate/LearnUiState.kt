package com.nilevia.dogbro.features.learn.ui.uistate

import com.nilevia.dogbro.features.learn.repository.models.Breed

sealed interface LearnUiState {
    data class Success(val breeds: List<Breed>) : LearnUiState
    object Error : LearnUiState
    object Loading : LearnUiState
}
