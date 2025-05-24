package com.nilevia.dogbro.features.learn.domain

import com.nilevia.dogbro.features.learn.domain.models.Breed
import javax.inject.Inject

class BreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {

    suspend fun getBreeds(): Result<List<Breed>> {
        return breedRepository.getBreeds()
    }

    suspend fun getBreedImages(breed: Breed): Result<List<String>> {
        return breedRepository.getBreedImages(breed.breed.lowercase(), breed.subBreed?.lowercase())
    }
}
