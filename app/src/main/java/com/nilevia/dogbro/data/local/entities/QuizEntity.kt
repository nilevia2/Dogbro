package com.nilevia.dogbro.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quiz")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
    val score: Float,
)
