import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.flexicon.latenightcommits.screen.CommitLogScreen

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
        CommitLogScreen(screenModifier)
    }
}

expect fun getPlatformName(): String