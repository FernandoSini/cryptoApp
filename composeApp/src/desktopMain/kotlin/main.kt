import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.*
import androidx.compose.ui.window.application
import screens.Desktop.MainScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    var action by remember { mutableStateOf("Last action: None") }
    var isOpen by remember { mutableStateOf(true) }
    val database = remember { getDatabaseBuilder() }
    //System.setProperty("apple.awt.application.name", "CryptoApp")
    //System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");

    Window(onCloseRequest = ::exitApplication, title = "Bitcoin") {

        window.rootPane.apply {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true)
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false)

        }

        MenuBar() {

            Menu("File", mnemonic = 'F') {
                Item(
                    "Copy",
                    onClick = { action = "Last action: Copy" },
                    shortcut = KeyShortcut(Key.C, ctrl = true)
                )


                Menu("Actions", mnemonic = 'A') {
                    CheckboxItem(
                        "Advanced settings",
                        checked = false,
                        onCheckedChange = {
                            //isSubmenuShowing = !isSubmenuShowing
                        }
                    )
                }
            }
        }

        MainScreen().Content()
    }
}