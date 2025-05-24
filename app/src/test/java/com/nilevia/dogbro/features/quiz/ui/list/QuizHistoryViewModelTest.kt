package com.nilevia.dogbro.features.quiz.ui.list

import app.cash.turbine.test
import com.nilevia.dogbro.features.quiz.domain.models.QuizResult
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizHistoryUseCase
import com.nilevia.dogbro.features.quiz.ui.uistate.HistoryUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizHistoryViewModelTest {
    private val useCase: QuizHistoryUseCase = mockk()
    private lateinit var viewModel: QuizHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = QuizHistoryViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getQuizHistory emits Success on success`() = testScope.runTest {
        val results = listOf(QuizResult(1, 2, 3))
        coEvery { useCase.getQuizHistory() } returns Result.success(results)
        viewModel.getQuizHistory()
        viewModel.historyUiState.test {
            assert(awaitItem() is HistoryUiState.Loading)
            assert(awaitItem() == HistoryUiState.Success(results))
        }
    }

    @Test
    fun `getQuizHistory emits Error on failure`() = testScope.runTest {
        coEvery { useCase.getQuizHistory() } returns Result.failure(Exception())
        viewModel.getQuizHistory()
        viewModel.historyUiState.test {
            assert(awaitItem() is HistoryUiState.Loading)
            assert(awaitItem() is HistoryUiState.Error)
        }
    }
} 