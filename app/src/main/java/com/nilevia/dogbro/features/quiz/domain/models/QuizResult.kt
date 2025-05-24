package com.nilevia.dogbro.features.quiz.domain.models

import com.nilevia.dogbro.data.local.entities.QuizEntity

data class QuizResult(
    val id: Int,
    val time: Long,
    val score: Float
)

fun QuizResult.toEntity() = QuizEntity(id, time, score)
