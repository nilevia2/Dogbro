package com.nilevia.dogbro.features.ui.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.domain.BreedUseCase
import com.nilevia.dogbro.features.repository.models.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LearnUiState {
    data class Success(val breeds: List<Breed>) : LearnUiState
    object Error : LearnUiState
    object Loading : LearnUiState
}

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val breedUseCase: BreedUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<LearnUiState>(LearnUiState.Loading)
    val uiState: StateFlow<LearnUiState> = _uiState

    fun getBreeds() {
        _uiState.value = LearnUiState.Loading
        viewModelScope.launch {
            breedUseCase.getBreeds()
                .onSuccess {
                    _uiState.value = LearnUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = LearnUiState.Error
                }
        }
    }


}