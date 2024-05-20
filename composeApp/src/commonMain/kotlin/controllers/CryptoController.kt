package controllers

import models.CryptoCoin
import services.Implementation.CryptoServiceImpl

class CryptoController(private val cryptoServiceImpl: CryptoServiceImpl) {


    suspend fun fetchData(body: Map<String, String>): Any? {
        val result = cryptoServiceImpl.fetchData(body)

        return result.fold(
            onSuccess = { value -> value as Set<CryptoCoin> },
            onFailure = { exception: Throwable -> exception.message })
        /*  if (result.getOrNull() is ErrorResponse) {
              return (result.getOrNull() as ErrorResponse).error.description

          } else {
              print("444: "+result.getOrNull())
              return result.getOrNull() as Set<Bitcoin>
             // return null
          }*/


    }

    suspend fun fetchSingleCrypto(body: Map<String, String>): CryptoCoin? {

        return null
    }


}