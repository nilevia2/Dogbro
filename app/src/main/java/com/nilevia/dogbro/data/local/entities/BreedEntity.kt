package com.nilevia.dogbro.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    val breed: String,
    val subBreed: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) 