package screens.Mobile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import models.CryptoCoin
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.Mobile.view.CryptoViewModel
import screens.Mobile.view.ThemeViewModel
import utlis.ThemeUI
import utlis.round

class CryptoListScreen : Screen {

    @OptIn(
        ExperimentalResourceApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class,
    )
    @Composable
    @Preview
    override fun Content() {

        var searchText by remember { mutableStateOf("") }
        var loading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        //var errorMessage by remember { mutableStateOf("") }
        //val cryptos by remember { mutableStateOf(emptySet<CryptoCoin>()) }
        val cryptoViewModel = viewModel<CryptoViewModel>() {
            CryptoViewModel()
        }
        val themeViewModel = viewModel<ThemeViewModel>() { ThemeViewModel() }
        val isDarkMode = themeViewModel.isDarkMode.collectAsState()

        val cryptos by cryptoViewModel.cryptoCoins.collectAsState()
        val errorMessage by cryptoViewModel.errorMessage.collectAsState()
        loading = cryptoViewModel.loading.collectAsState().value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = loading,
            onRefresh = { cryptoViewModel.fetchCryptoCoins(searchText) },
            refreshThreshold = 30.dp,
            refreshingOffset = 60.dp
        )

        LaunchedEffect(searchText) {
            scope.launch {
                cryptoViewModel.fetchCryptoCoins(searchText);
            }

        }

        if (loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    Modifier.height(50.dp).width(50.dp), ThemeUI().textFieldColor

                )
            }
        } else {
            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                CryptoItems(cryptos.toMutableList(), errorMessage, pullRefreshState, loading, isDarkMode.value)
                PullRefreshIndicator(
                    refreshing = loading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

            }


        }
    }

    @OptIn(
        ExperimentalResourceApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class,
    )
    @Composable
    @Preview
    fun CryptoItems(
        cryptos: List<CryptoCoin>,
        errorMessage: String,
        pullRefreshState: PullRefreshState,
        loading: Boolean,
        isDarkMode: Boolean,
    ) {
        val MILLION = 1000000L
        val BILLION = 1000000000L
        val TRILLION = 1000000000000L
        if (!cryptos.isNullOrEmpty()) {


            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 400.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(top = 20.dp)


            ) {
                items(cryptos) {
                    Card(
                        colors = CardDefaults.cardColors(if (!isDarkMode) ThemeUI().lightGray else ThemeUI().cardItemBlue),
                        modifier = Modifier.height(50.dp).padding(horizontal = 15.dp).clickable {
                            if (it.links !== null) {

                            }
                        },
                        content = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth(0.5f).padding(top = 5.dp),
                                    content = {
                                        Text(
                                            it.rank.toString(),
                                            style = TextStyle(color = if (!isDarkMode) ThemeUI().primaryBackground else ThemeUI().white)
                                        )
                                        Spacer(Modifier.padding(10.dp))
                                        Column(
                                            horizontalAlignment = Alignment.Start,

                                            ) {
                                            AsyncImage(
                                                ImageRequest.Builder(
                                                    LocalPlatformContext.current
                                                ).data(it.png64 ?: it.png32).crossfade(true)
                                                    .size(Size.ORIGINAL).build(),
                                                contentDescription = null,
                                                modifier = Modifier.size(40.dp),

                                                )
                                        }
                                        Spacer(Modifier.padding(5.dp))
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            content = {
                                                Text(
                                                    it.code.toString(),
                                                    style = TextStyle(color = if (!isDarkMode) ThemeUI().primaryBackground else ThemeUI().white),
                                                    fontSize = 14.sp,
                                                )
                                                Text(
                                                    it.name.toString(),
                                                    style = TextStyle(color =if (!isDarkMode) ThemeUI().primaryBackground else ThemeUI().white),
                                                    fontSize = 12.sp,
                                                )
                                            },
                                        )
                                    },
                                )
                                Spacer(Modifier.padding(10.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(horizontalAlignment = Alignment.Start) {
                                        Text(
                                            "\$${it.rate?.round(2).toString()}",
                                            style = TextStyle(color = if (!isDarkMode) ThemeUI().primaryBackground else ThemeUI().white),
                                            textAlign = TextAlign.Start,
                                        )
                                    }
                                    Spacer(Modifier.padding(5.dp))
                                    Column(horizontalAlignment = Alignment.Start) {
                                        Text(
                                            when {
                                                it?.cap == null -> "null"
                                                it.cap < MILLION -> "\$${it?.cap.toString()}k"
                                                it.cap < BILLION ->
                                                    "\$${
                                                        it?.cap?.times(100)?.div(MILLION)
                                                            ?.times(0.01)
                                                    }M"

                                                it.cap < TRILLION -> "\$${
                                                    it?.cap?.times(100)?.div(BILLION)?.times(0.01)
                                                        ?.round(2)
                                                }B"


                                                else -> "\$${
                                                    it?.cap?.times(100)?.div(TRILLION)?.times(0.01)
                                                }T"

                                            },
                                            style = TextStyle(color = if (!isDarkMode) ThemeUI().primaryBackground else ThemeUI().white),
                                            textAlign = TextAlign.Start,
                                        )

                                    }
                                }
                            }
                        }


                    )


                }


            }

        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(errorMessage, style = TextStyle(color = Color.White), color = ThemeUI().white)

            }

        }

    }

}