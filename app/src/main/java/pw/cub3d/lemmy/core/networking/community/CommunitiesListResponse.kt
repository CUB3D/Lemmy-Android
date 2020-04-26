package pw.cub3d.lemmy.core.networking.community

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.community.Community

@JsonClass(generateAdapter = true)
data class CommunitiesListResponse(
    val communities: List<Community>
)

