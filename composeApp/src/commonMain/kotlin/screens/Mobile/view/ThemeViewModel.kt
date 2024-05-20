package screens.Mobile.view

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {

    private var darkMode: MutableStateFlow<Boolean>;
    var isDarkMode: StateFlow<Boolean>;
    private var settings: Settings;

    init {
        settings = Settings()
        darkMode = MutableStateFlow(settings.getBoolean("darkMode", defaultValue = false))
        isDarkMode = darkMode.asStateFlow();
    }

    fun changeTheme() {
        viewModelScope.launch {
            when (darkMode.value) {
                true -> settings.putBoolean("darkMode", false)
                false -> settings.putBoolean("darkMode", true)
            }
            darkMode.value = settings.getBoolean("darkMode", false)
        }
    }


}