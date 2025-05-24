package com.nilevia.dogbro.features.learn.ui

import app.cash.turbine.test
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.learn.ui.uistate.LearnDetailUiState
import com.nilevia.dogbro.features.learn.ui.uistate.LearnUiState
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
class LearnViewModelTest {
    private val useCase: BreedUseCase = mockk()
    private lateinit var viewModel: LearnViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LearnViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getBreeds emits Success on success`() = testScope.runTest {
        val breeds = listOf(Breed("a"))
        coEvery { useCase.getBreeds() } returns Result.success(breeds)
        viewModel.getBreeds()
        viewModel.uiState.test {
            assert(awaitItem() is LearnUiState.Loading)
            assert(awaitItem() == LearnUiState.Success(breeds))
        }
    }

    @Test
    fun `getBreeds emits Error on failure`() = testScope.runTest {
        coEvery { useCase.getBreeds() } returns Result.failure(Exception())
        viewModel.getBreeds()
        viewModel.uiState.test {
            assert(awaitItem() is LearnUiState.Loading)
            assert(awaitItem() is LearnUiState.Error)
        }
    }

    @Test
    fun `getBreedDetail emits Success on success`() = testScope.runTest {
        val images = listOf("img1", "img2")
        val breed = Breed("a")
        coEvery { useCase.getBreedImages(breed) } returns Result.success(images)
        viewModel.getBreedDetail(breed)
        viewModel.detailUiState.test {
            assert(awaitItem() is LearnDetailUiState.Loading)
            assert(awaitItem() == LearnDetailUiState.Success(images))
        }
    }

    @Test
    fun `getBreedDetail emits Error on failure`() = testScope.runTest {
        val breed = Breed("a")
        coEvery { useCase.getBreedImages(breed) } returns Result.failure(Exception())
        viewModel.getBreedDetail(breed)
        viewModel.detailUiState.test {
            assert(awaitItem() is LearnDetailUiState.Loading)
            assert(awaitItem() is LearnDetailUiState.Error)
        }
    }
} 