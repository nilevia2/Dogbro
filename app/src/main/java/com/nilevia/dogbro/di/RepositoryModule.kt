package com.nilevia.dogbro.di

import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.local.dao.QuizDao
import com.nilevia.dogbro.data.remote.service.ApiService
import com.nilevia.dogbro.features.learn.domain.BreedRepository
import com.nilevia.dogbro.features.learn.repository.BreedRepositoryImpl
import com.nilevia.dogbro.features.quiz.domain.QuizRepository
import com.nilevia.dogbro.features.quiz.repository.QuizRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideBreedRepository(
        apiService: ApiService,
        breedDao: BreedDao
    ): BreedRepository {
        return BreedRepositoryImpl(apiService, breedDao)
    }

    @Provides
    @ViewModelScoped
    fun provideQuizRepository(
        quizDao: QuizDao
    ): QuizRepository{
        return QuizRepositoryImpl(quizDao)
    }
} 