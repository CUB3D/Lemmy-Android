package pw.cub3d.lemmy.core.networking.community

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityFollowRequest(
    val community_id: Int,
    val follow: Boolean,
    val auth: String
)