package com.nilevia.dogbro.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nilevia.dogbro.data.local.entities.BreedEntity

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<BreedEntity>)

    @Query("SELECT * FROM breeds")
    suspend fun getAllBreeds(): List<BreedEntity>
} 