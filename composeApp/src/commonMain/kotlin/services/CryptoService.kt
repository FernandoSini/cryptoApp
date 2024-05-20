package services

import com.fernandosini.nativetest.routes.HttpRoutes
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.json.Json
import models.CryptoCoin
import models.ErrorResponse

/*
abstract class CryptoService {
    abstract suspend fun fetchData(): Any?
}*/

interface CryptoService : DefaultService {
    open suspend fun fetchData(body: Map<String, String>): Any? {

        return try {

            if (body.get("codes").isNullOrEmpty()) {
                val url = HttpRoutes.DEFAULT_URL + "coins/list"
                var response = post(url, body)

                if (response.status.value >= 200 && response.status.value <= 299) {
                    return Json.decodeFromString<Set<CryptoCoin>>(
                        response.body()
                    )
                } else {
                    return Json.decodeFromString<ErrorResponse>(response.body())
                }
            } else {
                val url = HttpRoutes.DEFAULT_URL + "coins/map"
                var response = post(url, body)
                if (response.status.value >= 200 && response.status.value <= 299) {
                    return Json.decodeFromString<Set<CryptoCoin>>(
                        response.body()
                    )
                } else {
                    return Json.decodeFromString<ErrorResponse>(response.body())
                }

            }

        } catch (e: RedirectResponseException) {

            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {

            print("server response: ${e.message} +${e.response.status}")
            return e.response
        } catch (e: Exception) {
            println("Error Exception: ${e.message}")
            //   throw e
            return e
        }

    }

    open suspend fun fetchSingleCrypto(body: Map<String, String>): Any? {
        val url = HttpRoutes.DEFAULT_URL + "/coins/single"

        return try {
            var element = Json.decodeFromString<CryptoCoin>(post(url = url, body = body).body())

        } catch (e: RedirectResponseException) {

            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {

            print("server response: ${e.message} +${e.response.status}")
            return e.response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            //   throw e
            null
        }
    }
}