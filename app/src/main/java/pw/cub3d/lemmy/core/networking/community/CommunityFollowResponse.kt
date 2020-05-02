package pw.cub3d.lemmy.core.networking.community

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityFollowResponse(
    val community: CommunityView
)