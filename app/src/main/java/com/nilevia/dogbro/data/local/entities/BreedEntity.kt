package com.nilevia.dogbro.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val breed: String,
    val subBreed: String? = null
) 