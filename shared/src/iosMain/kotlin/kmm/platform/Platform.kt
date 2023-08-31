/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package kmm.platform

import core.common.PlatformOS
import platform.UIKit.UIDevice

actual class Platform {
    actual val os: PlatformOS = PlatformOS.IOS
    actual val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
