package com.nilevia.dogbro.data.remote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeCallIO(block: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
    try {
        val result = block()
        Result.success(result)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
} 