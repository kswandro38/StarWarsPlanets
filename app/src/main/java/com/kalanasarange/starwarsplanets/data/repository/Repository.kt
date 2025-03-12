package com.kalanasarange.starwarsplanets.data.repository

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.kalanasarange.starwarsplanets.api.ResponseState
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Singleton

private const val TAG = "Repository"

/**
 * Super Repository class is used to handle exceptions and fallback scenarios.
 * Technically this will handle all kind of exception which throws in middle of
 * the API calls flow. This will manage the exception appearance to the view layer
 * as well. This can be optimized to push direct string resources if there are
 * locale support needed.
 */
@Singleton
open class Repository {

    /**
     * Main exception handle function which take care of all type of exception which can
     * occur during communication.
     * IOException: are throws when there is an issue with establishing the connection with server
     * and communication issue
     * HTTPException: are throws when there is an issue with server side (HTTP errors) or any
     * issue which comes from serverside
     */
    fun <T> handleException(exception: Exception): ResponseState.Error<T> {
        exception.printStackTrace()
        return when (exception) {
            is IOException -> handleIOException(exception)
            is HttpException -> handleHttpException(exception)
            else -> ResponseState.Error(
                errorMessage = exception.message ?: "Oops, something went wrong."
            )
        }
    }

    /**
     * Handle IO exceptions of establishing the connection with server and communication issue
     */
    private fun <T> handleIOException(exception: IOException): ResponseState.Error<T> {
        Log.e(TAG, ("IOException: " + exception.message))
        return when (exception) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException -> {
                Log.e(TAG, ("SocketTimeoutException or ConnectException: " + exception.message))
                ResponseState.Error(
                    errorTitle = "Unable to Connect server",
                    errorMessage = "There is a problem connecting to the server. Check your network connection & try again.",
                    errorType = "Connection Error"
                )
            }
            else -> {
                Log.e(TAG, ("Something went wrong: " + exception.message))
                ResponseState.Error(
                    errorTitle = "Something went wrong",
                    errorMessage = exception.message
                        ?: "Oops, something went wrong. Please try again later."
                )
            }
        }
    }


    /**
     * Handle IO exceptions of server side (HTTP errors) or any issue which comes from serverside
     */
    private fun <T> handleHttpException(exception: HttpException): ResponseState.Error<T> {
        return try {
            getServerError(exception)
        } catch (exception: JsonSyntaxException) {
            Log.e(TAG, ("Failed to parse HttpException to JSON: " + exception.message))
            ResponseState.Error(
                errorMessage = exception.message ?: "Failed to parse Data"
            )
        } catch (exception: Exception) {
            Log.e(TAG, ("Something went wrong: " + exception.message))
            ResponseState.Error(
                errorMessage = exception.message ?: "Exception: Something went wrong:"
            )
        }
    }

    /**
     * Create and error representation template and feed the relevant error info to the module
     */
    private fun <T> getServerError(exception: HttpException): ResponseState.Error<T> {
        return ResponseState.Error(
            errorTitle = "Internal Server Error",
            errorMessage = exception.message
                ?: "Oops, something went wrong. Please try again later.",
            responseCode = exception.code(),
            errorType = "Server Error",
        )
    }
}