package screens.Desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import models.CryptoCoin
import utlis.ThemeUI

//import org.jetbrains.skiko.URIManager


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(headerTitle: String, cryptoCoins: List<CryptoCoin>, errorMessage: String) {

    Column(
        modifier = Modifier.fillMaxSize().background(
            Color(0xff131D46)
            //  Color(0xff100A39)
        ).padding(8.dp)
    ) {
        Text(headerTitle, fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            // modifier = Modifier.padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            content = {

                Text(
                    "Coin ",
                    style = TextStyle(color = Color.White),
                    textAlign = TextAlign.End
                )

                Text(
                    "Price ",
                    style = TextStyle(color = Color.White)
                )
                Text(
                    "Market Cap",
                    style = TextStyle(color = Color.White)
                )

            }
        )



        Spacer(Modifier.padding(10.dp))

        if (!cryptoCoins.isNullOrEmpty()) {
            LazyVerticalGrid(

                columns = GridCells.Adaptive(minSize = 700.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),


                ) {
                items(cryptoCoins) {

                    Card(
                        backgroundColor = ThemeUI().cardItemBlue,
                        modifier = Modifier.fillMaxWidth()
                            .height(50.dp).padding(horizontal = 10.dp)
                            .pointerHoverIcon(
                                icon = PointerIcon.Default,
                                overrideDescendants = true
                            ).clickable {
                                if (it.links !== null) {
                                    //   URIManager().openUri(it.links?.website!!)
                                }
                            },
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                // horizontalArrangement = Arrangement.spacedBy(20.dp),
                                horizontalArrangement = Arrangement.SpaceAround,

                                content = {
                                    Text(
                                        it.rank.toString(),
                                        style = TextStyle(color = Color.White)
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        content = {
                                            AsyncImage(
                                                ImageRequest.Builder(
                                                    LocalPlatformContext.current
                                                ).data(it.png64 ?: it.png32).crossfade(true)
                                                    .size(coil3.size.Size.ORIGINAL).build(),
                                                contentDescription = null,
                                                modifier = Modifier.size(40.dp),

                                                )
                                            Spacer(Modifier.padding(10.dp))
                                            Column(
                                                content = {
                                                    Text(
                                                        it.code.toString(),
                                                        style = TextStyle(color = Color.White),
                                                        fontSize = 16.sp,

                                                        )
                                                    Text(
                                                        it.name.toString(),
                                                        style = TextStyle(color = Color.White),
                                                        fontSize = 12.sp,
                                                    )
                                                },
                                            )
                                        })
                                    Text(
                                        it.rate.toString(),
                                        style = TextStyle(color = Color.White)
                                    )
                                    Text(
                                        it.cap.toString(),
                                        style = TextStyle(color = Color.White)
                                    )

                                }
                            )


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
                Text(errorMessage, style = TextStyle(color = Color.White))

            }
        }
    }


}
