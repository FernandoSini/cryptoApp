import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import screens.Mobile.HomeScreen

fun MainViewController() = ComposeUIViewController {
    val database = remember { getDatabaseBuilder() }
    Navigator(screen = HomeScreen(database), onBackPressed = { screen -> true }) { navigator ->
        SlideTransition(navigator)
    }
}