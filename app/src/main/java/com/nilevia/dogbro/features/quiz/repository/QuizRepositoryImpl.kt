package com.nilevia.dogbro.features.quiz.repository

import com.nilevia.dogbro.data.local.dao.QuizDao
import com.nilevia.dogbro.data.local.entities.QuizEntity
import com.nilevia.dogbro.data.remote.utils.safeCallIO
import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl(
    private val quizDao: QuizDao
): QuizRepository {
    override suspend fun getQuizResults(): Result<List<QuizEntity>>  = safeCallIO {
        quizDao.getQuizResults()
    }

    override suspend fun addQuizResult(quizResult: QuizEntity){
        safeCallIO {
            quizDao.insertResult(quizResult)
        }
    }
}