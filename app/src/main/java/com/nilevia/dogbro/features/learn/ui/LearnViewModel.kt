package com.nilevia.dogbro.features.learn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.ui.uistate.LearnDetailUiState
import com.nilevia.dogbro.features.learn.ui.uistate.LearnUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LearnViewModel @Inject constructor(
    private val breedUseCase: BreedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LearnUiState>(LearnUiState.Loading)
    val uiState: StateFlow<LearnUiState> = _uiState

    private val _detailUiState = MutableStateFlow<LearnDetailUiState>(LearnDetailUiState.Loading)
    val detailUiState: StateFlow<LearnDetailUiState> = _detailUiState

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

    fun getBreedDetail(breed: Breed) {
        _detailUiState.value = LearnDetailUiState.Loading
        viewModelScope.launch {
            breedUseCase.getBreedImages(breed)
                .onSuccess {
                    _detailUiState.value = LearnDetailUiState.Success(it)
                }
                .onFailure {
                    _detailUiState.value = LearnDetailUiState.Error
                }
        }
    }


}