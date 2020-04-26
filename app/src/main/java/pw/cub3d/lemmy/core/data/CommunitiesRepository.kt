package pw.cub3d.lemmy.core.data

import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunitiesRepository @Inject constructor(
    private val api: LemmyApiInterface
) {
//    fun getCommunities()
}