/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.common.PlatformOS
import kmm.platform.Platform
import ui.home.HomeScreen
import ui.theme.SharedTheme

@Composable
fun SharedApp(platform: Platform) {
    when (platform.os) {
        PlatformOS.ANDROID -> {
            HomeScreen(platform)
        }

        PlatformOS.IOS -> {
            SharedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).padding(top = 46.dp, bottom = 20.dp)
                ) {
                    HomeScreen(platform)
                }
            }
        }
    }
}

