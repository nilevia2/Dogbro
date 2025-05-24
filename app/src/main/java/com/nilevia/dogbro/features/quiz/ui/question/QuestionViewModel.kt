package com.nilevia.dogbro.features.quiz.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.learn.domain.mapper.getTitle
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val historyUseCase: QuizHistoryUseCase,
    private val questionUseCase: QuizQuestionUseCase,
    private val breedUseCase: BreedUseCase
) : ViewModel() {

    private val _questionsUiState = MutableStateFlow<QuestionUiState>(QuestionUiState.Loading)
    val questionsUiState = _questionsUiState.asStateFlow()


    private val _questionDetailUiState =
        MutableStateFlow<QuestionDetailUiState>(QuestionDetailUiState.Loading)
    val questionDetailUiState = _questionDetailUiState.asStateFlow()

    private var correctCount = 0
    private val questions = mutableListOf<Question>()
    private var currentQuestionIndex = 0


    fun submitQuizResult() {
        viewModelScope.launch {
            val result = QuizResult(
                System.currentTimeMillis(),
                correctCount,
                questions.size
            )
            historyUseCase.addQuizResult(result)
            _questionsUiState.value = QuestionUiState.Finish(result)
        }
    }

    fun getQuestions() {
        viewModelScope.launch {
            _questionsUiState.value = QuestionUiState.Loading
            questionUseCase.getQuizQuestion()
                .onSuccess {
                    questions.addAll(it)
                    _questionsUiState.value = QuestionUiState.Start
                }.onFailure {
                    _questionsUiState.value = QuestionUiState.Error
                }
        }
    }

    fun getQuestionDetail() {
        viewModelScope.launch {
            _questionsUiState.value = QuestionUiState.OnProgress(currentQuestionIndex)
            _questionDetailUiState.value = QuestionDetailUiState.Loading
            val selected = questions[currentQuestionIndex]
            if (selected.image.isNotBlank()) {
                _questionDetailUiState.value = QuestionDetailUiState.Success(selected)
            }
            // new question, fetch image first
            else breedUseCase.getBreedImages(selected.breed)
                .onSuccess { images ->
                    // update question bank with image
                    val updatedIndex = questions.indexOf(selected)
                    questions[updatedIndex] = selected.copy(image = images.first())
                    _questionDetailUiState.value =
                        QuestionDetailUiState.Success(selected.copy(image = images.first()))
                }
                .onFailure {
                    _questionDetailUiState.value = QuestionDetailUiState.Error
                }
        }
    }

    /**
     * return pair of correct or false and the answer
     */
    fun submitAnswer(questionNo: Int,answer: Breed) {
        val isCorrect = questions[questionNo].breed == answer
        if (isCorrect) correctCount++
        currentQuestionIndex++
        _questionDetailUiState.value = QuestionDetailUiState.Result(isCorrect, questions[questionNo].breed.getTitle())
        if (questionNo == questions.size - 1) submitQuizResult()

    }

}