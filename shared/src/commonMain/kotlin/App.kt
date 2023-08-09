/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

import androidx.compose.runtime.Composable
import core.common.Platform
import ui.home.HomeScreen
import ui.theme.CommonTheme

@Composable
fun CommonApp() {
    if (platform == Platform.ANDROID) {
        HomeScreen()
    } else {
        CommonTheme {
            HomeScreen()
        }
    }
}

