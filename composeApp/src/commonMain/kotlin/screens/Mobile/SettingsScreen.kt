package screens.Mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.Mobile.view.ThemeViewModel
import utlis.ThemeUI

class SettingsScreen : Screen {
    @OptIn(
        ExperimentalResourceApi::class, ExperimentalMaterialApi::class,
        ExperimentalMaterial3Api::class
    )
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings = Settings()
        val themeViewModel = viewModel<ThemeViewModel>() { ThemeViewModel() }
        val isDarkMode = themeViewModel.isDarkMode.collectAsState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.systemBars,
            containerColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Settings",
                            style = TextStyle(fontWeight = FontWeight.W600),
                            color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground,

                        ),
                    navigationIcon = {
                        if (navigator.canPop) {
                            IconButton(
                                onClick = {
                                    navigator.pop()
                                },
                                content = {
                                    Icon(
                                        imageVector = if (getPlatform().name.contains("iOS")) Icons.Filled.ArrowBackIosNew else Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "",
                                        tint = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white

                                    )
                                }
                            )
                        }
                    }
                )
            }
        )
        {
            Surface(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                color = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground

            ) {

                Card(
                    modifier = Modifier.height(50.dp).padding(horizontal = 15.dp),
                    backgroundColor = if (!isDarkMode.value) ThemeUI().lightGray else ThemeUI().cardItemBlue,
                    shape = RoundedCornerShape(10.dp),
                    onClick = {},
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier.height(25.dp)
                                .width(25.dp)
                                .background(
                                    color = if (!isDarkMode.value) ThemeUI().unspecified else ThemeUI().unspecified,
                                    shape = RoundedCornerShape(10.dp),
                                ),

                            ) {
                            Icon(
                                if (!isDarkMode.value) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white
                            )
                        }
                        Text(
                            "Change Theme",
                            color = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                        )
                        Switch(
                            checked = !isDarkMode.value,
                            onCheckedChange = { _ ->
                                themeViewModel.changeTheme()

                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = if (!isDarkMode.value) ThemeUI().primaryBackground else ThemeUI().white,
                                disabledCheckedThumbColor = if (!isDarkMode.value) ThemeUI().white else ThemeUI().primaryBackground
                            )
                        )
                    }
                }
            }


        }
    }
}