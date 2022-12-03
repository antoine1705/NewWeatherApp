package com.antoine.newweatherapp.presentation.base

import androidx.lifecycle.ViewModel
import com.antoine.newweatherapp.domain.usecase.base.AppResult
import com.antoine.newweatherapp.presentation.models.AppError
import com.antoine.newweatherapp.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val errorObs = SingleLiveEvent<AppError>()
    val loadingObs = SingleLiveEvent<Boolean>()

    open fun showLoading(isShow: Boolean) {
        loadingObs.postValue(isShow)
    }

    open fun handleFailure(error: AppResult) {
        when (error) {
            is AppResult.ResponseError -> {
                errorObs.postValue(AppError.ResponseError(error.errorModel.message))
            }
            is AppResult.NetworkError -> {
                errorObs.postValue(AppError.NetworkError)
            }
            is AppResult.PersistenceError -> {
                errorObs.postValue(AppError.PersistenceError)
            }
            else -> {
                errorObs.postValue(AppError.GenericError)
            }
        }
    }
}