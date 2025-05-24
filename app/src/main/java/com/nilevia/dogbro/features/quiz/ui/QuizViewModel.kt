package com.nilevia.dogbro.features.quiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.quiz.domain.models.Question
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizHistoryUseCase
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizQuestionUseCase
import com.nilevia.dogbro.features.quiz.ui.uistate.HistoryUiState
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionDetailUiState
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val historyUseCase: QuizHistoryUseCase,
    private val questionUseCase: QuizQuestionUseCase,
    private val breedUseCase: BreedUseCase
) : ViewModel() {

    private val _historyUiSate = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val historyUiState = _historyUiSate.asStateFlow()

    private val _questionsUiState = MutableStateFlow<QuestionUiState>(QuestionUiState.Loading)
    val questionsUiState = _questionsUiState.asStateFlow()


    private val _questionDetailUiState =
        MutableStateFlow<QuestionDetailUiState>(QuestionDetailUiState.Loading)
    val questionDetailUiState = _questionDetailUiState.asStateFlow()

    private var correctCount = 0
    private val questions = mutableListOf<Question>()

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

    fun submitQuizResult(quizResult: QuizResult) {
        viewModelScope.launch {
            historyUseCase.addQuizResult(quizResult)
        }
    }

    fun getQuestions() {
        viewModelScope.launch {
            _questionsUiState.value = QuestionUiState.Loading
            questionUseCase.getQuizQuestion()
                .onSuccess {
                    questions.addAll(it)
                    _questionsUiState.value = QuestionUiState.Success
                }.onFailure {
                    _questionsUiState.value = QuestionUiState.Error
                }
        }
    }

    fun getQuestionDetail(selected: Breed) {
        viewModelScope.launch {
            _questionDetailUiState.value = QuestionDetailUiState.Loading
            questions.find { it.breed == selected }?.let {
                // if already fetch image, return directly
                if (it.image.isNotBlank()) {
                    _questionDetailUiState.value = QuestionDetailUiState.Success(it)
                }
                // new question, fetch image first
                else breedUseCase.getBreedImages(it.breed)
                    .onSuccess { images ->
                        // update question bank with image
                        val updatedIndex = questions.indexOf(it)
                        questions[updatedIndex] = it.copy(image = images.first())
                        _questionDetailUiState.value = QuestionDetailUiState.Success(it.copy(image = images.first()))
                    }
                    .onFailure {
                        _questionDetailUiState.value = QuestionDetailUiState.Error
                    }
            }
        }
    }

}