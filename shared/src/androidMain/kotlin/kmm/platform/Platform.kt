/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package kmm.platform

import android.content.Context
import core.common.PlatformOS

actual class Platform(context: Context) {
    actual val os: PlatformOS = PlatformOS.ANDROID
    actual val name: String = "Max Android ${android.os.Build.VERSION.SDK_INT}"
}