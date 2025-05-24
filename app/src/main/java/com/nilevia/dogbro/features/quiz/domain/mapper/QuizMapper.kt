package com.nilevia.dogbro.features.quiz.domain.mapper

import com.nilevia.dogbro.data.local.entities.QuizEntity
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult

fun List<QuizEntity>?.mapToUi(): List<QuizResult> {
    return this?.map {
        QuizResult(
            it.time,
            it.score,
            it.maxScore
        )
    }?: emptyList()
}