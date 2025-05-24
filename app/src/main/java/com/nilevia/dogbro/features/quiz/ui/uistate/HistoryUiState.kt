package com.nilevia.dogbro.features.quiz.ui.uistate

import com.nilevia.dogbro.features.quiz.domain.models.QuizResult

sealed interface HistoryUiState{
    data class Success(val quizHistory: List<QuizResult>): HistoryUiState
    data object Error: HistoryUiState
    data object Loading: HistoryUiState

}