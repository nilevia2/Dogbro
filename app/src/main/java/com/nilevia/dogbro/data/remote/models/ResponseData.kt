package com.nilevia.dogbro.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val data: T?
)