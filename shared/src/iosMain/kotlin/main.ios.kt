import androidx.compose.ui.window.ComposeUIViewController
import core.common.Platform

actual val platformName: String = "iOS"

actual val platform: Platform = Platform.IOS

fun MainViewController() = ComposeUIViewController { CommonApp() }