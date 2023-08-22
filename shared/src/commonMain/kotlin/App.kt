/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

import androidx.compose.runtime.Composable
import core.common.PlatformOS
import kmm.platform.Platform
import ui.home.HomeScreen
import ui.theme.SharedTheme

@Composable
fun SharedApp(platform: Platform) {
    if (platform.os == PlatformOS.ANDROID) {
        HomeScreen(platform)
    } else {
        SharedTheme {
            HomeScreen(platform)
        }
    }
}

