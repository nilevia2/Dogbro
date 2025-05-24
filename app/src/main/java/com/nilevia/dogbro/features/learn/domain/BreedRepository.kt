package com.nilevia.dogbro.features.learn.domain

import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed

/**
 * skip DTO as server just return primitive value
 */
interface BreedRepository {
    suspend fun getBreeds(): Result<List<BreedEntity>>
    suspend fun getBreedImages(breed:String, subBreed: String? = null): Result<List<String>>
}