/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

import android.content.Context
import androidx.compose.runtime.Composable
import kmm.platform.Platform

@Composable
fun MainAndroidView(context: Context) = AndroidTheme {
    SharedApp(Platform(context))
}
