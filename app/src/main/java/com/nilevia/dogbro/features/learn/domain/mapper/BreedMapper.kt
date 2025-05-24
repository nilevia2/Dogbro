package com.nilevia.dogbro.features.learn.domain.mapper

import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed

fun List<BreedEntity>?.mapToUi(): List<Breed> {
    return this?.map { data ->
        Breed(
            data.breed,
            data.subBreed,
            "${data.breed.replaceFirstChar { it.uppercase() }} - ${data.subBreed?.replaceFirstChar { it.uppercase() }}"
        )
    } ?: emptyList()
}