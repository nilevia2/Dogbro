package com.nilevia.dogbro.features.quiz.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizHistoryUseCase
import com.nilevia.dogbro.features.quiz.ui.uistate.HistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizHistoryViewModel @Inject constructor(
    private val historyUseCase: QuizHistoryUseCase,
) : ViewModel() {

    private val _historyUiSate = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val historyUiState = _historyUiSate.asStateFlow()


    fun getQuizHistory() {
        viewModelScope.launch {
            _historyUiSate.value = HistoryUiState.Loading
            historyUseCase.getQuizHistory().onSuccess {
                _historyUiSate.value = HistoryUiState.Success(it)
            }.onFailure {
                _historyUiSate.value = HistoryUiState.Error
            }
        }
    }

}