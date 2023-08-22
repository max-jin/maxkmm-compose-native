/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package data.spacex

import core.spacex.RocketLaunch

class SpacexRepositoryImpl() : SpacexRepository {

    private val spacexService: SpacexService = SpacexService()

    @Throws(Exception::class)
    override suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return spacexService.getAllLaunches()
    }
}