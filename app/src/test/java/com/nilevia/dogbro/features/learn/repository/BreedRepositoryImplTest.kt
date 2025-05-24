package com.nilevia.dogbro.features.learn.repository

import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.data.remote.service.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BreedRepositoryImplTest {
    private val apiService: ApiService = mockk(relaxed = true)
    private val dao: BreedDao = mockk(relaxed = true)
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setUp() {
        repository = BreedRepositoryImpl(apiService, dao)
    }

    @Test
    fun `getBreeds returns local if not empty`() = runBlocking {
        val entities = listOf(BreedEntity("a", null))
        coEvery { dao.getAllBreeds() } returns entities
        val result = repository.getBreeds()
        assert(result.isSuccess)
        assert(result.getOrNull() == entities)
    }

    @Test
    fun `getBreeds fetches remote if local empty`() = runBlocking {
        coEvery { dao.getAllBreeds() } returns emptyList()
        coEvery { apiService.getBreeds() } returns mockk { coEvery { data } returns emptyMap() }
        coEvery { dao.insertAll(any()) } returns Unit
        repository.getBreeds()
        coVerify { apiService.getBreeds() }
    }

    @Test
    fun `getBreedImages calls correct api for subBreed`() = runBlocking {
        coEvery { apiService.getSubBreedImages("a", "b", any()) } returns mockk { coEvery { data } returns listOf("img1") }
        val result = repository.getBreedImages("a", "b", 1)
        assert(result.isSuccess)
        assert(result.getOrNull() == listOf("img1"))
    }

    @Test
    fun `getBreedImages calls correct api for breed`() = runBlocking {
        coEvery { apiService.getBreedImages("a", any()) } returns mockk { coEvery { data } returns listOf("img2") }
        val result = repository.getBreedImages("a", null, 1)
        assert(result.isSuccess)
        assert(result.getOrNull() == listOf("img2"))
    }
} 