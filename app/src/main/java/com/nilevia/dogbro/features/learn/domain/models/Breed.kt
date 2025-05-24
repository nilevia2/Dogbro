package com.nilevia.dogbro.features.learn.domain.models


data class Breed(
    val breed: String,
    val subBreed: String? = null,
    val displayedTitle: String = breed
)