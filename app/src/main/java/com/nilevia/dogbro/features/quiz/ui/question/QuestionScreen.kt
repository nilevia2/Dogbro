package com.nilevia.dogbro.features.quiz.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.quiz.ui.question.components.FinishLayout
import com.nilevia.dogbro.features.quiz.ui.question.components.QuestionLayout
import com.nilevia.dogbro.features.quiz.ui.question.components.ResultComponent
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionDetailUiState
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionUiState
import com.nilevia.dogbro.utils.components.ErrorComponent
import com.nilevia.dogbro.utils.components.LoadingComponent
import com.nilevia.dogbro.utils.components.SubScreen

@Composable
fun MainQuestionScreen(
    viewModel: QuestionViewModel = hiltViewModel(), onBack: () -> Unit
) {
    val mainUiState by viewModel.questionsUiState.collectAsState()
    val detailUiState by viewModel.questionDetailUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getQuestions()
    }
    SubScreen(
        "Quiz", onBack
    ) {
        when (mainUiState) {
            QuestionUiState.Error -> ErrorComponent("Error loading question") {
                viewModel.getQuestions()
            }

            QuestionUiState.Loading -> LoadingComponent()

            QuestionUiState.Start -> StartLayout {
                viewModel.getQuestionDetail()
            }

            is QuestionUiState.OnProgress -> OnProgressLayout(
                detailUiState = detailUiState,
                number = (mainUiState as QuestionUiState.OnProgress).no,
                loadQuestion = { viewModel.getQuestionDetail() },
                submitAnswer = { no, answer -> viewModel.submitAnswer(no, answer) },
                nextQuestion = { viewModel.getQuestionDetail() }
            )

            is QuestionUiState.Finish -> FinishLayout(
                onClose = onBack,
                quizResult = (mainUiState as QuestionUiState.Finish).quizResult
            )
        }
    }
}

@Composable
fun StartLayout(
    startQuiz: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Are you ready?", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { startQuiz.invoke() }) {
            Text(text = "Start")
        }
    }
}

@Composable
fun OnProgressLayout(
    detailUiState: QuestionDetailUiState,
    number: Int,
    loadQuestion: () -> Unit,
    submitAnswer: (Int, Breed) -> Unit,
    nextQuestion: () -> Unit
) {
    when (detailUiState) {
        QuestionDetailUiState.Error -> ErrorComponent("Error loading question") {
            loadQuestion.invoke()
        }

        QuestionDetailUiState.Loading -> LoadingComponent()

        is QuestionDetailUiState.Result -> ResultComponent(detailUiState) {
            nextQuestion.invoke()
        }

        is QuestionDetailUiState.Success -> QuestionLayout(number, detailUiState, submitAnswer)

    }
}




