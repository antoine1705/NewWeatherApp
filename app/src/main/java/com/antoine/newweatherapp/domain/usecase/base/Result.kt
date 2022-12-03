package com.antoine.newweatherapp.domain.usecase.base

import com.antoine.newweatherapp.domain.models.ErrorModel

@Suppress("UNCHECKED_CAST")
sealed class AppResult {
    class Success<T>(val successData: T) : AppResult()
    class ResponseError(val errorModel: ErrorModel) : AppResult()
    object NetworkError : AppResult()
    object GenericError : AppResult()
    object PersistenceError : AppResult()

    fun <K> handleResult(
        successBlock: (K) -> Unit = {},
        failureBlock: (AppResult) -> Unit = {},
    ) {
        when (this) {
            is Success<*> -> (successData as? K)?.let { successBlock(it) }
            else -> failureBlock(this)
        }
    }
}