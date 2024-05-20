package screens.Desktop


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.mutableStateOf
import models.CryptoCoin
import org.jetbrains.compose.resources.ExperimentalResourceApi

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import controllers.CryptoController
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import services.Implementation.CryptoServiceImpl
import utlis.ThemeUI


class MainScreen : Screen {

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        var cryptoCoins by remember { mutableStateOf(emptySet<CryptoCoin>()) }
        var errorMessage by remember { mutableStateOf("") }
        var currentProgress by remember { mutableStateOf(0f) }
        var loading by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        var headerTitle by remember { mutableStateOf("Title") }
        var isRefreshing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val newScope = CoroutineScope(SupervisorJob())
        val cryptoServiceImpl = CryptoServiceImpl();
        val cryptoController = CryptoController(cryptoServiceImpl)
        val settings = Settings()
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                scope.launch {
                    loadProgress { progress ->
                        currentProgress = progress
                    }
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
                            is Set<*> -> cryptoCoins = data as Set<CryptoCoin>
                            else -> errorMessage = data.toString()
                        }


                    } catch (e: ClientRequestException) {
                        print("aqui error:" + e.message)
                    }
                    isRefreshing = false;
                }
            }
        )


        LaunchedEffect(searchText) {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
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
                        body["sort"] = "rank"
                        body["order"] = "ascending"
                        body["meta"] = true.toString()
                        cryptoController.fetchData(body = body)
                    }
                    val data = when (response) {
                        is Set<*> -> response.toSet()
                        else -> response
                    }
                    when (data) {
                        is Set<*> -> cryptoCoins = data as Set<CryptoCoin>
                        else -> errorMessage = data.toString()
                    }


                } catch (e: ClientRequestException) {
                    print("aqui error:" + e.message)
                }

                loading = false

            }
        }
        Scaffold(
            backgroundColor = ThemeUI().primaryBackground,

            modifier = Modifier.pullRefresh(pullRefreshState)

        ) {

            Row {
                SidePanel(onMenuSelected = {
                    headerTitle = it
                    searchText = ""
                    cryptoCoins = emptySet()

                }, onCryptoSearched = { searchedCrypto, header ->
                    searchText = searchedCrypto
                    headerTitle = header
                    cryptoCoins = emptySet()

                })
                if (loading || isRefreshing)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            Modifier.height(50.dp).width(50.dp), Color(0xff252894)

                        )
                    }
                else MainContent(headerTitle, cryptoCoins.toMutableList(), errorMessage)
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.Top)
                )
            }
        }


    }

    suspend fun loadProgress(updateProgress: (Float) -> Unit) {
        for (i in 1..100) {
            updateProgress(i.toFloat() / 100)
            delay(100)
        }
    }
}

