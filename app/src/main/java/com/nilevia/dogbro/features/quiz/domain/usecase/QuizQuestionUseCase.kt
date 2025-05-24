package com.nilevia.dogbro.features.quiz.domain.usecase

import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import javax.inject.Inject

class QuizQuestionUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
    private val breedUseCase: BreedUseCase
) {

    suspend fun getQuizQuestion(): Result<List<Breed>> {
        return breedUseCase.getBreeds().map {
            it.shuffled().take(10)
        }
    }

    suspend fun calculateResult(
        selectedAnswer: List<String>,
        correctAnswer: List<String>
    ): Int {
        var correctCount = 0
        for (i in correctAnswer.indices) {
            if (selectedAnswer[i] == correctAnswer[i]) {
                correctCount++
            }
        }
        return correctCount
    }
}