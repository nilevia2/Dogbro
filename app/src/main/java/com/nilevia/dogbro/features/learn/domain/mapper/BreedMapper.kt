package com.nilevia.dogbro.features.learn.domain.mapper

import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed

fun List<BreedEntity>?.mapToUi(): List<Breed> {
    return this?.map { it ->
        Breed(
            it.breed,
            it.subBreed
        )
    } ?: emptyList()
}

/**
 * use to construct name from breed and subBreed
 */
fun Breed.getTitle(): String{
    val subBreedFormatted =
        if (subBreed.isNullOrBlank()) ""
        else " - " + subBreed.replaceFirstChar { it.uppercase() }
    return breed.replaceFirstChar { it.uppercase() } + subBreedFormatted
}