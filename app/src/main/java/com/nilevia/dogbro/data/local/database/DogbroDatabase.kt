package com.nilevia.dogbro.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.entities.BreedEntity

@Database(
    entities = [BreedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DogbroDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
} 