package com.nilevia.dogbro.features.domain

import com.nilevia.dogbro.features.repository.models.Breed
import javax.inject.Inject

class BreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {

    suspend fun getBreeds(): Result<List<Breed>> {
        return breedRepository.getBreeds()
    }
}
