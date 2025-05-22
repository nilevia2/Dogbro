package com.nilevia.dogbro.features.repository.models

import com.nilevia.dogbro.data.local.entities.BreedEntity

data class Breed(
    val breed: String,
    val subBreed: String? = null
)

fun BreedEntity.toBreed(): Breed = Breed(
    breed = this.breed,
    subBreed = this.subBreed
) 