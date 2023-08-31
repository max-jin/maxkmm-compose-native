/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package data.spacex

import core.spacex.RocketLaunch
import data.util.httpclient.appHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*

class SpacexService {

    @Throws(Exception::class)
    suspend fun getAllLaunches(): List<RocketLaunch> {
        return appHttpClient.get(SPACEX_API_URL).body()
    }

    companion object {
        private const val SPACEX_API_URL = "https://api.spacexdata.com/v5/launches"
    }
}