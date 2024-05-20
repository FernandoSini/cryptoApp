package screens.Mobile

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repository.database.AppDatabase
import utlis.ThemeUI

class SplashScreen : Screen {


    @OptIn(
        ExperimentalResourceApi::class, ExperimentalMaterialApi::class,
        ExperimentalMaterial3Api::class
    )
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("splash") },
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
                                        tint = ThemeUI().primaryBackground

                                    )
                                }
                            )
                        }

                    },
                )
            },
        ) { }


    }
}