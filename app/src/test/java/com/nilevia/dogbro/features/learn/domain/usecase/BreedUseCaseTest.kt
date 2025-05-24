package com.nilevia.dogbro.features.learn.domain.usecase

import com.nilevia.dogbro.features.learn.domain.BreedRepository
import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BreedUseCaseTest {
    private val repository: BreedRepository = mockk(relaxed = true)
    private lateinit var useCase: BreedUseCase

    @Before
    fun setUp() {
        useCase = BreedUseCase(repository)
    }

    @Test
    fun `getBreeds returns mapped results`() = runBlocking {
        val entities = listOf(BreedEntity("a", null))
        coEvery { repository.getBreeds() } returns Result.success(entities)
        val result = useCase.getBreeds()
        assert(result.isSuccess)
        assert(result.getOrNull()?.first()?.main == "a")
    }

    @Test
    fun `getBreedImages calls repository`() = runBlocking {
        val breed = Breed("a")
        val images = listOf("img1", "img2")
        coEvery { repository.getBreedImages("a", null, any()) } returns Result.success(images)
        val result = useCase.getBreedImages(breed)
        assert(result.isSuccess)
        assert(result.getOrNull() == images)
    }
} 