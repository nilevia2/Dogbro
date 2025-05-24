package com.nilevia.dogbro.features.quiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizHistoryUseCase
import com.nilevia.dogbro.features.quiz.ui.uistate.HistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val historyUseCase: QuizHistoryUseCase
): ViewModel() {

    private val _historyUiSate = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val historyUiState = _historyUiSate.asStateFlow()

    fun getQuizHistory(){
        viewModelScope.launch {
            historyUseCase.getQuizHistory().onSuccess {
                _historyUiSate.value = HistoryUiState.Success(it)
            }.onFailure {
                _historyUiSate.value = HistoryUiState.Error
            }
        }
    }

    fun submitQuizResult(quizResult: QuizResult){
        viewModelScope.launch {
            historyUseCase.addQuizResult(quizResult)
        }
    }

    /**
     * question logic
     * option1
     * get random 10 breeds.
     * each screen fetch image of that breed. multiple selection: 1 of selected breed and 3 random; set
     * //// but this required several screen which is wasting resources.
     *
     * option2:
     * get 10 random breed
     * fetch one image of each breed, multiple selection: 1 of selected breed and 3 random; set
     * show as list
     * //// wasting resource on http handshake bcs each breed different api.
     * //// api doesn't provide fetch multiple image of multiple breed.
     * //// worst case on load will fetch 10 api and 10 image
     * //// slow load
     *
     * option3:
     * carousell
     * get 10 breed.
     * each page fetch image based on breed
     * // better init load option
     * // more complex ui with carousell (handle back and next)
     *
     */

    fun getQuestion(){

    }

}