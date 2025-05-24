package com.nilevia.dogbro.features.quiz.domain.usecase

import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class QuizQuestionUseCaseTest {
    private val breedUseCase: BreedUseCase = mockk()
    private lateinit var useCase: QuizQuestionUseCase

    @Before
    fun setUp() {
        useCase = QuizQuestionUseCase(breedUseCase)
    }

    @Test
    fun `getQuizQuestion returns 10 questions with 4 options each`() = runBlocking {
        val breeds = (1..20).map { Breed("breed$it") }
        coEvery { breedUseCase.getBreeds() } returns Result.success(breeds)
        val result = useCase.getQuizQuestion()
        assert(result.isSuccess)
        val questions = result.getOrNull()!!
        assert(questions.size == 10)
        questions.forEach { q ->
            assert(q.options.size == 4)
            assert(q.options.contains(q.breed))
        }
    }

    @Test
    fun `getQuizQuestion returns failure if breedUseCase fails`() = runBlocking {
        coEvery { breedUseCase.getBreeds() } returns Result.failure(Exception())
        val result = useCase.getQuizQuestion()
        assert(result.isFailure)
    }
} 