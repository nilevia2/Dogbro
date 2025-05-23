package com.nilevia.dogbro.features.ui.learn.uistate

import com.nilevia.dogbro.features.repository.models.Breed

sealed interface LearnUiState {
    data class Success(val breeds: List<Breed>) : LearnUiState
    object Error : LearnUiState
    object Loading : LearnUiState
}
