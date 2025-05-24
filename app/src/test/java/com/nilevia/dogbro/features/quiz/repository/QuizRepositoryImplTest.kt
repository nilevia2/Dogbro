package com.nilevia.dogbro.features.quiz.repository

import com.nilevia.dogbro.data.local.dao.QuizDao
import com.nilevia.dogbro.data.local.entities.QuizEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class QuizRepositoryImplTest {
    private val dao: QuizDao = mockk(relaxed = true)
    private lateinit var repository: QuizRepositoryImpl

    @Before
    fun setUp() {
        repository = QuizRepositoryImpl(dao)
    }

    @Test
    fun `getQuizResults calls dao`() = runBlocking {
        val entities = listOf(QuizEntity(1, 2, 3))
        coEvery { dao.getQuizResults() } returns entities
        val result = repository.getQuizResults()
        assert(result.isSuccess)
        assert(result.getOrNull() == entities)
    }

    @Test
    fun `addQuizResult calls dao`() = runBlocking {
        val entity = QuizEntity(1, 2, 3)
        repository.addQuizResult(entity)
        coVerify { dao.insertResult(entity) }
    }
} 