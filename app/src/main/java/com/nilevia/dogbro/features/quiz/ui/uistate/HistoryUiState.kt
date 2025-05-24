package com.nilevia.dogbro.features.quiz.ui.uistate

import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.quiz.domain.models.Question
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult

sealed interface HistoryUiState {
    data class Success(val quizHistory: List<QuizResult>) : HistoryUiState
    data object Error : HistoryUiState
    data object Loading : HistoryUiState
}

sealed interface QuestionUiState {
    data object Start : QuestionUiState
    data object Loading : QuestionUiState
    data object Error : QuestionUiState
    data class OnProgress(val no: Int) : QuestionUiState
    data class Finish(val quizResult: QuizResult) : QuestionUiState
}

sealed interface QuestionDetailUiState {
    data class Success(val question: Question) : QuestionDetailUiState
    data object Loading : QuestionDetailUiState
    data object Error : QuestionDetailUiState
    data class Result(val status: Boolean, val correctAnswer: String) : QuestionDetailUiState
}