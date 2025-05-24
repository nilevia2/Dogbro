package com.nilevia.dogbro.features.quiz.domain.models

data class Question(
    val breed: String,
    var image: String = "",
    val options: List<String>
)