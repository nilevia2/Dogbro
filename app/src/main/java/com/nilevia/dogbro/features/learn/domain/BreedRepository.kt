package com.nilevia.dogbro.features.learn.domain

import com.nilevia.dogbro.features.learn.repository.models.Breed

interface BreedRepository {
    suspend fun getBreeds(): Result<List<Breed>>
    suspend fun getBreedImages(breed:String, subBreed: String? = null): Result<List<String>>
}