package com.nilevia.dogbro.features.quiz.ui.question

import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.usecase.BreedUseCase
import com.nilevia.dogbro.features.quiz.domain.models.Question
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizHistoryUseCase
import com.nilevia.dogbro.features.quiz.domain.usecase.QuizQuestionUseCase
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionDetailUiState
import com.nilevia.dogbro.features.quiz.ui.uistate.QuestionUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionViewModelTest {
    private val historyUseCase: QuizHistoryUseCase = mockk(relaxed = true)
    private val questionUseCase: QuizQuestionUseCase = mockk()
    private val breedUseCase: BreedUseCase = mockk()
    private lateinit var viewModel: QuestionViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = QuestionViewModel(historyUseCase, questionUseCase, breedUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getQuestions emits Start on success`() = runTest {
        val questions = listOf(Question(Breed("a"),"", listOf(Breed("a"))))
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(questions)
        viewModel.getQuestions()
        advanceUntilIdle()
        assertTrue(viewModel.questionsUiState.value is QuestionUiState.Start)
    }

    @Test
    fun `getQuestions emits Error on failure`() = runTest {
        coEvery { questionUseCase.getQuizQuestion() } returns Result.failure(Exception())
        viewModel.getQuestions()
        advanceUntilIdle()
        assertTrue(viewModel.questionsUiState.value is QuestionUiState.Error)
    }

    @Test
    fun `submitAnswer emits Result and Finish at last question`() = runTest {
        val breed = Breed("a")
        val questions = listOf(Question(breed,"", listOf(breed)))
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(questions)
        coEvery { historyUseCase.addQuizResult(any()) } returns Unit
        viewModel.getQuestions()
        advanceUntilIdle()
        viewModel.submitAnswer(0, breed)
        advanceUntilIdle()
        assertTrue(viewModel.questionDetailUiState.value is QuestionDetailUiState.Result)
        assertTrue(viewModel.questionsUiState.value is QuestionUiState.Finish)
    }

    @Test
    fun `getQuestionDetail emits Success if image exists`() = runTest {
        val breed = Breed("a")
        val question = Question(breed, image = "img.jpg", options = listOf(breed))
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(listOf(question))
        viewModel.getQuestions()
        advanceUntilIdle()
        viewModel.getQuestionDetail()
        advanceUntilIdle()
        assertTrue(viewModel.questionDetailUiState.value is QuestionDetailUiState.Success)
    }

    @Test
    fun `getQuestionDetail fetches image if not present and emits Success`() = runTest {
        val breed = Breed("a")
        val question = Question(breed, image = "", options = listOf(breed))
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(listOf(question))
        coEvery { breedUseCase.getBreedImages(breed) } returns Result.success(listOf("img.jpg"))
        viewModel.getQuestions()
        advanceUntilIdle()
        viewModel.getQuestionDetail()
        advanceUntilIdle()
        assertTrue(viewModel.questionDetailUiState.value is QuestionDetailUiState.Success)
    }

    @Test
    fun `getQuestionDetail emits Error if image fetch fails`() = runTest {
        val breed = Breed("a")
        val question = Question(breed, image = "", options = listOf(breed))
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(listOf(question))
        coEvery { breedUseCase.getBreedImages(breed) } returns Result.failure(Exception())
        viewModel.getQuestions()
        advanceUntilIdle()
        viewModel.getQuestionDetail()
        advanceUntilIdle()
        assertTrue(viewModel.questionDetailUiState.value is QuestionDetailUiState.Error)
    }

    @Test
    fun `submitAnswer emits Result for wrong answer and does not finish if not last question`() = runTest {
        val breed1 = Breed("a")
        val breed2 = Breed("b")
        val questions = listOf(
            Question(breed1, options = listOf(breed1, breed2)),
            Question(breed2, options = listOf(breed1, breed2))
        )
        coEvery { questionUseCase.getQuizQuestion() } returns Result.success(questions)
        viewModel.getQuestions()
        advanceUntilIdle()
        viewModel.submitAnswer(0, breed2) // wrong answer for first question
        advanceUntilIdle()
        assertTrue(viewModel.questionDetailUiState.value is QuestionDetailUiState.Result)
        // Not last question, so should not emit Finish
        assertTrue(viewModel.questionsUiState.value !is QuestionUiState.Finish)
    }
} 