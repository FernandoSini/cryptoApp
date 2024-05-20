package services

import com.fernandosini.myapplication.ktor.KtorClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface DefaultService {

    suspend fun get(url: String): HttpResponse {
        val bitcoinApiKey = "919f0cd0-ccbc-494e-9086-03573ee84912";
        val response = KtorClient.httpClient.get(url) {
            headers {
                append("x-api-key", bitcoinApiKey)
            }
            contentType(ContentType.Application.Json)
        };
        return response
    }

    suspend fun post(url: String, body: Map<String, String>?): HttpResponse {
        val bitcoinApiKey = "919f0cd0-ccbc-494e-9086-03573ee84912";
        val response = KtorClient.httpClient.post(url) {
            if (!body.isNullOrEmpty()) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("x-api-key", bitcoinApiKey)
            }

            contentType(ContentType.Application.Json)
        }
        return response
    }
}