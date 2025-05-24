package com.nilevia.dogbro.features.quiz.domain.usecase

import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import com.nilevia.dogbro.features.quiz.domain.models.Question
import javax.inject.Inject

class QuizQuestionUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
    private val breedUseCase: BreedUseCase
) {

    /**
     * get list of breeds from database / remote
     * shuffle the list and get 10 breeds
     * get questions by remove correct one from shuffled list and get 3 random options
     * add correct one at the end and shuffled again
     */
    suspend fun getQuizQuestion(): Result<List<Question>> {
        return breedUseCase.getBreeds().map { breeds ->
            breeds.shuffled().take(10).map { correctBreed ->
                val options = (breeds - correctBreed)
                    .shuffled()
                    .take(3)
                    .plus(correctBreed)
                    .shuffled()
                Question(
                    breed = correctBreed,
                    options = options
                )
            }
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