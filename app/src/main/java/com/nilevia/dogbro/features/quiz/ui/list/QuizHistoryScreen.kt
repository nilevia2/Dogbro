package com.nilevia.dogbro.features.quiz.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.features.quiz.ui.QuizViewModel
import com.nilevia.dogbro.features.quiz.ui.uistate.HistoryUiState
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizHistoryScreen(viewModel: QuizViewModel = hiltViewModel(), onStartQuiz: () -> Unit) {
    val uiState by viewModel.historyUiState.collectAsState()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(Unit) {
        viewModel.getQuizHistory()
    }

    val showQuizButton = uiState is HistoryUiState.Success

    Scaffold(
        floatingActionButton = {
            if (showQuizButton) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    FloatingActionButton(onClick = {
                        // dummy
                        viewModel.submitQuizResult(QuizResult(time = Clock.systemUTC().millis(), score = 9, maxScore = 10))
                        viewModel.submitQuizResult(QuizResult(time = Clock.systemUTC().millis()+10000, score = 1, maxScore = 10))
                        viewModel.submitQuizResult(QuizResult(time = Clock.systemUTC().millis()+20000, score = 3, maxScore = 10))
                        viewModel.getQuizHistory()
                    }) {
                        Text("Start Quiz", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    ) {
        when (uiState) {
            HistoryUiState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error loading history")
                    Button(onClick = { viewModel.getQuizHistory() }) {
                        Text("Retry")
                    }
                }
            }

            HistoryUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is HistoryUiState.Success -> {
                val history = (uiState as HistoryUiState.Success).quizHistory

                if (history.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("No history found", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Let's start a new quiz", style = MaterialTheme.typography.bodyLarge)
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState
                    ) {
                        items(history) { item ->
                            Column {
                                HistoryItem(item)
                                Divider(
                                    color = Color.Gray,
                                    thickness = 0.5.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(history: QuizResult) {
    val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
    val date = formatter.format(Date(history.time))
    val score = history.score.toString() + "/" + history.maxScore
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray
        )
        Text(
            text = "Score : $score",
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHistory() {
    val sample = listOf(
        QuizResult(12345567, 8, 10),
        QuizResult(12345567, 10, 10),
        QuizResult(12345567, 2, 10),
        QuizResult(12345567, 4, 10)
    )
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(sample) { item ->
                Column {
                    HistoryItem(item)
                    Divider(
                        color = Color.Gray,
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}
