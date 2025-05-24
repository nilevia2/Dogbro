package com.nilevia.dogbro.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quiz")
data class QuizEntity(
    val time: Long,
    val score: Int,
    val maxScore: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
