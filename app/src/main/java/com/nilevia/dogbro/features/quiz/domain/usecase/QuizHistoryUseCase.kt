package com.nilevia.dogbro.features.quiz.domain.usecase

import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import com.nilevia.dogbro.features.quiz.domain.mapper.mapToUi
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.features.quiz.domain.models.toEntity
import javax.inject.Inject

class QuizHistoryUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend fun getQuizHistory(): Result<List<QuizResult>> {
       return quizRepository.getQuizResults().map { it.mapToUi() }
    }

    suspend fun addQuizResult(quizResult: QuizResult){
        quizRepository.addQuizResult(quizResult.toEntity())
    }
}