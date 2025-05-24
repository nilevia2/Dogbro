package com.nilevia.dogbro.features.quiz.domain.models

import org.junit.Test

class QuizResultTest {
    @Test
    fun `toEntity maps fields correctly`() {
        val result = QuizResult(1, 2, 3)
        val entity = result.toEntity()
        assert(entity.time == 1L)
        assert(entity.score == 2)
        assert(entity.maxScore == 3)
    }
} 