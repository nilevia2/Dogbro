package com.nilevia.dogbro.data.local

import com.nilevia.dogbro.data.local.entities.BreedEntity

fun mapToEntities(map: Map<String, List<String>>?): List<BreedEntity> {
    return map?.flatMap { (breed, subBreeds) ->
        if (subBreeds.isEmpty()) {
            listOf(BreedEntity(breed = breed))
        } else {
            subBreeds.map { sub -> BreedEntity(breed = breed, subBreed = sub) }
        }
    }?: emptyList()
} 