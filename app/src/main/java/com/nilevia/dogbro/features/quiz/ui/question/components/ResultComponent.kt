package com.nilevia.dogbro.features.quiz.ui.question.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionDetailUiState

@Composable
fun ResultComponent(detailUiState: QuestionDetailUiState.Result, nextQuestion: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (detailUiState.status) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Text("Correct")

        } else {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
            Text("The correct answer is: ${detailUiState.correctAnswer}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(nextQuestion) {
            Text("Next")
        }
    }
}