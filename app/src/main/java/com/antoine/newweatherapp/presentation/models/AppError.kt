package com.antoine.newweatherapp.presentation.models

sealed class AppError {
    class ResponseError(val msg: String) : AppError()
    object NetworkError : AppError()
    object GenericError : AppError()
    object PersistenceError : AppError()
}