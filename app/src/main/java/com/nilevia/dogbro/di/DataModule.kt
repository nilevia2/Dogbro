package com.nilevia.dogbro.di

import android.app.Application
import androidx.room.Room
import com.nilevia.dogbro.data.local.database.DogbroDatabase
import com.nilevia.dogbro.data.remote.retrofit.RetrofitProvider
import com.nilevia.dogbro.data.remote.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val databaseName = "dob_bro"
    private const val BASE_URL = "https://dog.ceo/api/"

    @Provides
    @Singleton
    fun provideDatabase(context: Application): DogbroDatabase {
        return Room.databaseBuilder(context, DogbroDatabase::class.java, databaseName)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider.create(BASE_URL)
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideBreedDao(database: DogbroDatabase) = database.breedDao()

} 