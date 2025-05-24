package com.nilevia.dogbro.data.remote.service

import com.nilevia.dogbro.data.remote.models.ResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): ResponseData<Map<String, List<String>>>

    @GET("breed/{breed}/images/random/{total}}")
    suspend fun getBreedImages(
        @Path("breed") breed: String,
        @Path("total") total: Int
    ): ResponseData<List<String>>

    @GET("breed/{breed}/{subBreed}/images/random/{total}")
    suspend fun getSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String,
        @Path("total") total: Int
    ): ResponseData<List<String>>
}