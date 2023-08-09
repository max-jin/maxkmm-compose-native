import androidx.compose.runtime.Composable
import core.common.Platform

actual val platformName: String = "Android"

actual val platform: Platform = Platform.ANDROID

@Composable
fun MainAndroidView() = AndroidTheme {
    CommonApp()
}
