package com.nilevia.dogbro.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nilevia.dogbro.data.local.entities.QuizEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(quiz: QuizEntity)

    @Query("SELECT * FROM quiz order by time desc")
    suspend fun getQuizResults(): List<QuizEntity>
}