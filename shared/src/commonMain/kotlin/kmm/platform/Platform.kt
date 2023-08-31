/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package kmm.platform

import core.common.PlatformOS

expect class Platform {
    val os: PlatformOS
    val name: String
}