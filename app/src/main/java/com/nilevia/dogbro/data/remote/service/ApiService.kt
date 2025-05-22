package com.nilevia.dogbro.data.remote.service

import com.nilevia.dogbro.data.remote.models.ResponseData
import retrofit2.http.GET

interface ApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): ResponseData<Map<String, List<String>>>
}