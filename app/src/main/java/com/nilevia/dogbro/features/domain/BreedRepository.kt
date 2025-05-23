package com.nilevia.dogbro.features.domain

import com.nilevia.dogbro.features.repository.models.Breed

interface BreedRepository {
    suspend fun getBreeds(): Result<List<Breed>>
    suspend fun getBreedImages(breed:String, subBreed: String? = null): Result<List<String>>
}