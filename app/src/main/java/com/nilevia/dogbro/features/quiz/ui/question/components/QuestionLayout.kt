package com.nilevia.dogbro.features.quiz.ui.question.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nilevia.dogbro.features.learn.domain.mapper.getTitle
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.quiz.domain.models.Question
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionDetailUiState

@Composable
fun QuestionLayout(number: Int,detailUiState: QuestionDetailUiState.Success, submitAnswer: (Int, Breed) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
    ) {
        Text(text = "Question ${number+1}")
        Spacer(modifier = Modifier.height(24.dp))
        val question: Question = detailUiState.question
        Image(
            painter = rememberAsyncImagePainter(question.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        question.options.forEach { option ->
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { submitAnswer.invoke(number, option) }) {
                Text(option.getTitle())
            }
        }


    }
}