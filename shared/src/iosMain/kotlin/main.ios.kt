import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }

@Composable
actual fun BrowserLink(
    url: String,
    enabled: Boolean,
    modifier: Modifier,
    content: @Composable (BoxScope.() -> Unit),
) {
    Box(
        modifier = modifier.clickable(enabled = enabled) {
            NSURL.URLWithString(url)?.let {
                UIApplication.sharedApplication.openURL(it)
            } ?: println("Invalid URL passed as BrowserLink: $url")
        },
        content = content,
    )
}
