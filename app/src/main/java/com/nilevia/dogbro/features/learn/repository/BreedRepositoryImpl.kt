package com.nilevia.dogbro.features.learn.repository

import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.data.local.mapToEntities
import com.nilevia.dogbro.data.remote.service.ApiService
import com.nilevia.dogbro.data.remote.utils.safeCallIO
import com.nilevia.dogbro.features.learn.domain.BreedRepository

class BreedRepositoryImpl(
    private val apiService: ApiService,
    private val breedDao: BreedDao
) : BreedRepository {
    override suspend fun getBreeds(): Result<List<BreedEntity>> = safeCallIO {
        val localEntities = breedDao.getAllBreeds()
        localEntities.ifEmpty {
            val response = apiService.getBreeds()
            val entities = mapToEntities(response.data)
            breedDao.insertAll(entities)
            entities
        }
    }

    override suspend fun getBreedImages(breed: String, subBreed: String?): Result<List<String>> = safeCallIO {
        if (subBreed != null) {
            apiService.getSubBreedImages(breed, subBreed).data
        } else {
            apiService.getBreedImages(breed).data
        }.orEmpty()
    }
}