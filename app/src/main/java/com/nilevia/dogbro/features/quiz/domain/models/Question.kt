package com.nilevia.dogbro.features.quiz.domain.models

import com.nilevia.dogbro.features.learn.domain.models.Breed

data class Question(
    val breed: Breed,
    var image: String = "",
    val options: List<String>
)