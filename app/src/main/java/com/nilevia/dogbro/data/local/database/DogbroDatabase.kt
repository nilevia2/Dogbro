package com.nilevia.dogbro.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.dao.QuizDao
import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.data.local.entities.QuizEntity

@Database(
    entities = [BreedEntity::class, QuizEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DogbroDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun quizDao(): QuizDao
} 