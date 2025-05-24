package com.nilevia.dogbro.features.quiz.domain.mapper

import com.nilevia.dogbro.data.local.entities.QuizEntity
import org.junit.Test

class QuizMapperTest {
    @Test
    fun `mapToUi maps list correctly`() {
        val entities = listOf(QuizEntity(1, 2, 3))
        val results = entities.mapToUi()
        assert(results.size == 1)
        assert(results[0].score == 2)
    }

    @Test
    fun `mapToUi handles null list`() {
        val results = (null as List<QuizEntity>?).mapToUi()
        assert(results.isEmpty())
    }

    @Test
    fun `mapToUi handles empty list`() {
        val results = emptyList<QuizEntity>().mapToUi()
        assert(results.isEmpty())
    }
} 