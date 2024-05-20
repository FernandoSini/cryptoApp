package screens.Desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utlis.ThemeUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SidePanel(
    onMenuSelected: (header: String) -> Unit,
    onCryptoSearched: (searchedCryptoCoin: String, header: String) -> Unit
) {

    var searchedCryptoCoin by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //val bitmap = useResource("image.png"){loadImageBitmap(it)}
        //Image(bitmap,"logo", Modifier)
        // useResource pra carregar imagem ou Image(painterResource(Res.drawable.compose_multiplatform), null)
        Spacer(modifier = Modifier.padding(18.dp))
        BasicTextField(

            modifier = Modifier.fillMaxWidth().height(30.dp)
                .background(

                    color =ThemeUI().textFieldColor,
                    shape = RoundedCornerShape(10.dp),
                ).indicatorLine(
                    enabled = false,
                    isError = false,
                    colors = TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = Color.White),
                    interactionSource = remember { MutableInteractionSource() },
                    focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                    unfocusedIndicatorLineThickness = 0.dp,

                    ),
            singleLine = true,
            value = searchedCryptoCoin,
            onValueChange = { searchedCryptoCoin = it },
            textStyle = TextStyle(color = Color.White),
            cursorBrush = SolidColor(Color.White)


        ) {
            TextFieldDefaults.TextFieldDecorationBox(

                placeholder = {
                    Text(
                        "Search",
                        color = Color.White,
                        style = TextStyle(fontSize = 12.sp),
                        //modifier = Modifier.padding(vertical = 5.dp)
                    )
                },
                /*colors = TextFieldDefaults.textFieldColors(

                    backgroundColor = Color(0xff181A5B),
                    unfocusedIndicatorColor = Color.White,
                    textColor = Color.White

                ),*/
                enabled = false,
                innerTextField = it,
                interactionSource = remember { MutableInteractionSource() },
                singleLine = true,
                value = searchedCryptoCoin,
                visualTransformation = VisualTransformation.None,

                trailingIcon = {
                    IconButton(
                        onClick = {
                            onCryptoSearched(searchedCryptoCoin, "Results for $searchedCryptoCoin")
                        },
                        modifier = Modifier.size(40.dp)
                            .pointerHoverIcon(
                                icon = PointerIcon.Default,
                                overrideDescendants = true
                            ),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search button",
                                tint = Color.White,
                                modifier = Modifier.height(20.dp)

                            )
                        }
                    )
                },
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 0.dp, bottom = 0.dp
                ),
            )
        }
    }
}
/* OutlinedTextField(
             modifier = Modifier.fillMaxWidth().height(46.dp),
             singleLine = true,
             shape = RoundedCornerShape(10.dp),
             placeholder = {
                 Text(
                     "Search",
                     color = Color.White,
                     style = TextStyle(fontSize = 12.sp),
                     //modifier = Modifier.padding(vertical = 5.dp)
                 )
             },
             colors = TextFieldDefaults.textFieldColors(
                 backgroundColor = Color(0xff181A5B),
                 unfocusedIndicatorColor = Color.White,
                 textColor = Color.White
             ),
             value = searchedBitcoin,
             onValueChange = { searchedBitcoin = it },
             trailingIcon = {
                 IconButton(
                     onClick = {
                         onBitcoinSearched(searchedBitcoin, "Results for $searchedBitcoin")
                     },
                     modifier = Modifier.size(40.dp)
                         .pointerHoverIcon(icon = PointerIcon.Default, overrideDescendants = true),
                     content = {
                         Icon(
                             imageVector = Icons.Default.Search,
                             contentDescription = "Search button",
                             tint = Color.White,
                             modifier = Modifier.height(20.dp)

                         )
                     }
                 )
             }


         )*/