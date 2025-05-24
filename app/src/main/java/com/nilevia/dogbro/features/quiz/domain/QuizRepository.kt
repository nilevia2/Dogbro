package com.nilevia.dogbro.features.quiz.domain

import com.nilevia.dogbro.data.local.entities.QuizEntity

interface QuizRepository {
    suspend fun getQuizResults(): Result<List<QuizEntity>>
    suspend fun addQuizResult(quizResult: QuizEntity)
}