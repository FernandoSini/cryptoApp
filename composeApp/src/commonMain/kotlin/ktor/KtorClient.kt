package com.fernandosini.myapplication.ktor

/*import android.os.Build
import androidx.annotation.RequiresApi*/
import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.engine.android.Android
//import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.coroutines.handleCoroutineException
import kotlinx.serialization.json.Json


object KtorClient {

    private val serializeJson = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }


    val httpClient = HttpClient() {
        /*install(Logging) {
            level = LogLevel.ALL
        }*/
        install(ContentNegotiation) {
            json(json = serializeJson)
        }

        install(HttpRequestRetry) {
            //function enables retrying a request if a 5xx response is received from a server and specifies the number of retries.
            retryOnServerErrors(5)
            //specifies an exponential delay between retries, which is calculated using the Exponential backoff algorithm.
            exponentialDelay()
            //If you want to add some additional params in header
            modifyRequest { request ->
                request.headers.append("x-retry-count", 2.toString())

            }
        }

        //If you want to change user agent
        install(UserAgent) {
            agent = "Ktor"
        }

        install(DefaultRequest) {
            val bitcoinApiKey = "919f0cd0-ccbc-494e-9086-03573ee84912";
            headers.append("x-api-key", bitcoinApiKey)
            headers.appendIfNameAbsent("X-custom-header", "Some Value")
            contentType(ContentType.Application.Json)
        }
        /*HttpResponseValidator {
            validateResponse {
                handleResponseExceptionWithRequest { exception, request ->
                    val clientException =
                        exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                    if (exceptionResponse.status >= HttpStatusCode.Forbidden) {
                        throw CustomResponseException(exception.message, null, null)
                    }
                }
            }
        }*/

        ResponseObserver {
            val timeDifference = it.responseTime.timestamp - it.requestTime.timestamp
            val protocolVersion = it.version
        }
    }
}