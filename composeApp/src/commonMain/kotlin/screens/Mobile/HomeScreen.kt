package screens.Mobile

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults.shape
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.RoomDatabase
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import repository.database.AppDatabase
import repository.database.getRoomDatabase
import screens.Mobile.view.CryptoViewModel
import screens.Mobile.view.ThemeViewModel
import services.Implementation.CryptoServiceImpl
import utlis.ThemeUI

class HomeScreen(var databaseBuilder: RoomDatabase.Builder<AppDatabase>) : Screen {
    @OptIn(
        ExperimentalResourceApi::class,
        ExperimentalMaterial3Api::class,
        ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class,
    )
    @Composable
    @Preview
    override fun Content() {
        //val myDatabase = remember { databaseBuilder.build() }
        val room = remember { getRoomDatabase(databaseBuilder) }
        val tooltipState = remember { BasicTooltipState() }
        val userDao = room.getUserDao()

        val themeViewModel = viewModel<ThemeViewModel>() { ThemeViewModel() }
        val isDarkMode = themeViewModel.isDarkMode.collectAsState()
        var tabIndex by remember { mutableStateOf(0) }
        var tabs = listOf("Home", "About")
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        val cryptoService = CryptoServiceImpl()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground,
            contentWindowInsets = WindowInsets.systemBars,
            topBar = {
                Surface(shadowElevation = 0.dp) {
                    Column() {
                        CenterAlignedTopAppBar(
                            windowInsets = WindowInsets.statusBars,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground,

                                ),
                            actions = {
                                BasicTooltipBox(
                                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                    state = tooltipState,
                                    tooltip = {
                                        Text(
                                            "Open menu",
                                            color = ThemeUI().white
                                        )
                                    },

                                    ) {
                                    IconButton(
                                        onClick = {
                                            navigator.push(SettingsScreen())
                                        },

                                        /*modifier = Modifier.combinedClickable(
                                            enabled = true,
                                            onLongClick = { print("here") },
                                            onClick = { null }),*/

                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "",
                                            tint = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,

                                            )
                                    }
                                }
                            },

                            title = {
                                BasicTextField(

                                    modifier = Modifier.fillMaxWidth().height(40.dp)
                                        .padding(start = 20.dp),
                                    /*  .background(

                                          color = if (!isDarkMode.value) ThemeUI().white else ThemeUI().textFieldColor,
                                          shape = RoundedCornerShape(10.dp),
                                      ).indicatorLine(
                                          enabled = false,
                                          isError = false,
                                          colors = TextFieldDefaults.colors(
                                              unfocusedIndicatorColor = if (!isDarkMode.value) ThemeUI().transparent else ThemeUI().white,



                                              ),
                                          interactionSource = remember { MutableInteractionSource() },
                                          focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                                          unfocusedIndicatorLineThickness = 0.dp,

                                          ),*/ // esse só funciona com o material normal

                                    singleLine = true,
                                    value = "",
                                    onValueChange = { it },
                                    textStyle = TextStyle(color = Color.White),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(onDone = null),
                                    cursorBrush = SolidColor(if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white)


                                ) {
                                    TextFieldDefaults.DecorationBox(
                                        colors = TextFieldDefaults.colors(
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            disabledContainerColor = if (!isDarkMode.value) Color.Unspecified else ThemeUI().textFieldColor,
                                            focusedContainerColor = if (!isDarkMode.value) Color.Unspecified else ThemeUI().textFieldColor

                                        ),

                                        placeholder = {
                                            Text(
                                                "Search",
                                                color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                                                style = TextStyle(fontSize = 12.sp),
                                                //modifier = Modifier.padding(vertical = 5.dp)
                                            )
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        enabled = false,
                                        innerTextField = it,
                                        interactionSource = remember { MutableInteractionSource() },
                                        singleLine = true,
                                        value = "",
                                        visualTransformation = VisualTransformation.None,

                                        trailingIcon = {
                                            IconButton(
                                                onClick = {
                                                    //  onCryptoSearched(searchedCryptoCoin, "Results for $searchedCryptoCoin")
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
                                                        tint = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
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
                            },

                            navigationIcon = {
                                /*IconButton(onClick = {}) {

                                    Icon(
                                        modifier = Modifier.padding(start = 10.dp),
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "", tint = ThemeUI().white,


                                        )


                                }*/
                            }
                        )
                        TabRow(
                            containerColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground,
                            contentColor = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                            selectedTabIndex = tabIndex,
                            modifier = Modifier.height(50.dp).fillMaxWidth(),
                            divider = {},
                            indicator = { tabPositions ->
                                TabRowDefaults.SecondaryIndicator(
                                    color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                                )
                            },

                            ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    modifier = Modifier.fillMaxWidth(),
                                    selected = tabIndex == index,
                                    onClick = { tabIndex = index },

                                    text = {
                                        Text(
                                            title,
                                            style = TextStyle(color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white),
                                            color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "",
                                            tint = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) {
            Surface(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                color = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground
            ) {
                when (tabIndex) {
                    0 -> CryptoListScreen().Content()

                }
            }
        }
    }
}