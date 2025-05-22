package com.nilevia.dogbro.di

import com.nilevia.dogbro.data.local.dao.BreedDao
import com.nilevia.dogbro.data.remote.service.ApiService
import com.nilevia.dogbro.features.domain.BreedRepository
import com.nilevia.dogbro.features.repository.BreedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BreedModule {
    @Provides
    @ViewModelScoped
    fun provideBreedRepository(
        apiService: ApiService,
        breedDao: BreedDao
    ): BreedRepository {
        return BreedRepositoryImpl(apiService, breedDao)
    }
} 