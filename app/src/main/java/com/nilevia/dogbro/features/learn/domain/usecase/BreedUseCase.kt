package com.nilevia.dogbro.features.learn.domain.usecase

import com.nilevia.dogbro.features.learn.domain.BreedRepository
import com.nilevia.dogbro.features.learn.domain.mapper.mapToUi
import com.nilevia.dogbro.features.learn.domain.models.Breed
import javax.inject.Inject

class BreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    private val DEFAULT_IMAGE_COUNT = 10
    suspend fun getBreeds(): Result<List<Breed>> {
        return breedRepository.getBreeds().map { it.mapToUi() }
    }

    suspend fun getBreedImages(breed: Breed, total: Int = DEFAULT_IMAGE_COUNT): Result<List<String>> {
        return breedRepository.getBreedImages(
            breed.breed.lowercase(),
            breed.subBreed?.lowercase(),
            total
        )
    }
}
