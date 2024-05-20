package services.Implementation

import models.CryptoCoin
import models.ErrorResponse
import services.CryptoService

open class CryptoServiceImpl : CryptoService {
    override suspend fun fetchData(body: Map<String, String>): Result<Any?> {
        // return super.fetchData(body) as Set<CryptoCoin> // erro está aqui
        var data = super.fetchData(body)
        /*  if (data is Set<*>) {
              return Result.success(data as Set<CryptoCoin>);
          } else {

              return Result.success(data)
          }*/

        return when (data) {
            is Set<*> -> return Result.success<Set<CryptoCoin>>(data.toSet() as Set<CryptoCoin>)
            is ErrorResponse -> return Result.failure<ErrorResponse>(Exception("${data.error.description}"))
            else -> return Result.failure(Exception("Conversion error, invalid json"))
        }
    }


    override suspend fun fetchSingleCrypto(body: Map<String, String>): CryptoCoin {
        return super.fetchSingleCrypto(body) as CryptoCoin
    }


}