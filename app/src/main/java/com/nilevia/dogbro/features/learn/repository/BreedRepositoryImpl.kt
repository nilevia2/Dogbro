package com.nilevia.dogbro.features.learn.repository

import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.mapToEntities
import com.nilevia.dogbro.data.remote.service.ApiService
import com.nilevia.dogbro.data.remote.utils.callApi
import com.nilevia.dogbro.features.learn.domain.BreedRepository
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.domain.models.toBreed

class BreedRepositoryImpl(
    private val apiService: ApiService,
    private val breedDao: BreedDao
) : BreedRepository {
    override suspend fun getBreeds(): Result<List<Breed>> = callApi {
        val localEntities = breedDao.getAllBreeds()
        if (localEntities.isNotEmpty()) {
            localEntities.map { it.toBreed() }
        } else {
            val response = apiService.getBreeds()
            val entities = mapToEntities(response.data)
            breedDao.insertAll(entities)
            entities.map { it.toBreed() }
        }
    }

    override suspend fun getBreedImages(breed: String, subBreed: String?): Result<List<String>> = callApi {
        if (subBreed != null) {
            apiService.getSubBreedImages(breed, subBreed).data
        } else {
            apiService.getBreedImages(breed).data
        }.orEmpty()
    }
}