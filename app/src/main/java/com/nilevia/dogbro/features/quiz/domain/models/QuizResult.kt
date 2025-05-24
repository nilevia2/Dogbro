package com.nilevia.dogbro.features.quiz.domain.models

import com.nilevia.dogbro.data.local.entities.QuizEntity

data class QuizResult(
    val time: Long,
    val score: Int,
    val maxScore: Int
)

fun QuizResult.toEntity() = QuizEntity(time = time, score = score, maxScore = maxScore)
