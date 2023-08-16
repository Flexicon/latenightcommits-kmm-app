import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.flexicon.latenightcommits.screen.CommitLogScreen
import dev.flexicon.latenightcommits.vm.CommitLogViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App() {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = Color(0xFF374D78),
            surface = Color.White,
            onBackground = Color.White,
            onSurface = Color.Black,
        )
    ) {
        val screenModifier = Modifier.background(MaterialTheme.colors.background)
        val commitLogViewModel = getViewModel(Unit, viewModelFactory { CommitLogViewModel() })

        CommitLogScreen(
            viewModel = commitLogViewModel,
            modifier = screenModifier,
        )
    }
}

expect fun getPlatformName(): String

@Composable
expect fun BrowserLink(
    url: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit),
)
