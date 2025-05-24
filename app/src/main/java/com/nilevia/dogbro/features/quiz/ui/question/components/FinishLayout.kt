package com.nilevia.dogbro.features.quiz.ui.question.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult

@Composable
fun FinishLayout(
    onClose: () -> Unit,
    quizResult: QuizResult
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Finish", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            "Your score is ${quizResult.score} out of ${quizResult.maxScore}",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Button(onClick = { onClose.invoke() }) {
            Text("Close")
        }

    }
}