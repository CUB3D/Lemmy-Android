package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.Community

@JsonClass(generateAdapter = true)
data class CommunitiesListResponse(
    val communities: List<Community>
)

