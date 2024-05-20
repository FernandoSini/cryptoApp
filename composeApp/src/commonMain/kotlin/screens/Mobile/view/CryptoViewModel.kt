package screens.Mobile.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import controllers.CryptoController
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.CryptoCoin
import services.Implementation.CryptoServiceImpl

class CryptoViewModel(

) : ViewModel() {

    private var cryptoController: CryptoController;
    private var listCryptos = MutableStateFlow(emptySet<CryptoCoin>())
    val cryptoCoins = listCryptos.asStateFlow();
    private var message = MutableStateFlow("")
    var errorMessage = message.asStateFlow()
    var loading = MutableStateFlow(false);


    init {
        cryptoController = CryptoController(CryptoServiceImpl())

        //fetchCryptoCoins(searchText)

    }

     fun fetchCryptoCoins(searchText: String) {
        loading.value = true
        viewModelScope.launch {
            try {

                val response = if (searchText.isNullOrEmpty()) {
                    val body = mutableMapOf<String, String>()
                    body["sort"] = "rank"
                    body["order"] = "ascending"
                    body["meta"] = true.toString();
                    cryptoController.fetchData(body = body)
                } else {
                    val body = mutableMapOf<String, String>()
                    body["codes"] = setOf(searchText).toList().toString()
                    body["meta"] = true.toString()
                    cryptoController.fetchData(body = body)
                }
                val data = when (response) {
                    is Set<*> -> response.toSet()
                    else -> response
                }
                when (data) {
                    is Set<*> -> listCryptos.value = data as Set<CryptoCoin>
                    else -> message.value = data.toString()
                }

            } catch (e: ClientRequestException) {
                print("aqui error:" + e.message)
            }
            loading.value = false
        }

    }

}