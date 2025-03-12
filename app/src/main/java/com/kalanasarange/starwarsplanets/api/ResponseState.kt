package com.kalanasarange.starwarsplanets.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * This Sealed class will maintain the response status with serverside.
 * Class will capable of update the states of Idle, Loading, Success and Error
 * when there are situations accordingly
 * Success State can wrap the data obj as child obj and carry the success state
 * message as well
 * Error State can wrap the error response, type of error and error info in order
 * to demonstration purpose of view layer
 */
sealed class ResponseState<T> {
    class Idle<T> : ResponseState<T>()
    class Loading<T> : ResponseState<T>()
    data class Success<T>(
        val data: T,
        val message: String? = null
    ) : ResponseState<T>()

    @Parcelize
    data class Error<T>(
        val errorTitle: String? = null,
        val errorMessage: String,
        val errorType: String? = null,
        val responseCode: Int? = null
    ) : ResponseState<T>(), Parcelable
}