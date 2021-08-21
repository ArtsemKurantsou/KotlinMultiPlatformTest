package com.kurantsov.domain.interactor

interface SuspendInteractor<T> {
    suspend fun execute(): T
}