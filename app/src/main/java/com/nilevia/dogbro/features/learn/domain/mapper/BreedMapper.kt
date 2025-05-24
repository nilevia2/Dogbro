package com.nilevia.dogbro.features.learn.domain.mapper

import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed

fun List<BreedEntity>?.mapToUi(): List<Breed> {
    return this?.map { it ->
        Breed(
            main = it.breed,
            sub = it.subBreed
        )
    } ?: emptyList()
}

/**
 * use to construct name from breed and subBreed
 */
fun Breed.getTitle(): String{
    val subBreedFormatted =
        if (sub.isNullOrBlank()) ""
        else " - " + sub.replaceFirstChar { it.uppercase() }
    return main.replaceFirstChar { it.uppercase() } + subBreedFormatted
}