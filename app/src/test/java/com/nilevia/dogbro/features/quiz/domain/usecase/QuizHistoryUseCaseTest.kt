package com.nilevia.dogbro.features.quiz.domain.usecase

import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.data.local.entities.QuizEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class QuizHistoryUseCaseTest {
    private val repository: QuizRepository = mockk(relaxed = true)
    private lateinit var useCase: QuizHistoryUseCase

    @Before
    fun setUp() {
        useCase = QuizHistoryUseCase(repository)
    }

    @Test
    fun `getQuizHistory returns mapped results`() = runBlocking {
        val entities = listOf(QuizEntity(1, 2, 3))
        coEvery { repository.getQuizResults() } returns Result.success(entities)
        val result = useCase.getQuizHistory()
        assert(result.isSuccess)
        assert(result.getOrNull()?.first()?.score == 2)
    }

    @Test
    fun `addQuizResult calls repository`() = runBlocking {
        val quizResult = QuizResult(1, 2, 3)
        useCase.addQuizResult(quizResult)
        coVerify { repository.addQuizResult(any()) }
    }
} 