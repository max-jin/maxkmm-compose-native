/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

import androidx.compose.ui.window.ComposeUIViewController
import kmm.platform.Platform

fun MainViewController() = ComposeUIViewController { SharedApp(Platform()) }