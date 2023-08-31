/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package data.util.httpclient

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import util.json.appJson

val appHttpClient = HttpClient {
    install(ContentNegotiation) {
        json(appJson)
    }
}