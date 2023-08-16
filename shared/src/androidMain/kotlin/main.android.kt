import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView() = App()

@Composable
actual fun BrowserLink(
    url: String,
    modifier: Modifier,
    content: @Composable (BoxScope.() -> Unit),
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.clickable {
            runCatching { Uri.parse(url) }.fold(
                { context.startActivity(Intent(Intent.ACTION_VIEW, it)) },
                { println("Invalid URL passed as BrowserLink: $url") },
            )
        },
        content = content,
    )
}
