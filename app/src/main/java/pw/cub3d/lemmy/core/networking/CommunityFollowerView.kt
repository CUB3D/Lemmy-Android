package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityFollowerView(
    val id: Int,
    val community_id: Int,
    val user_id: Int,
    val published: String,
    val user_name: String,
    val avatar: String?,
    val community_name: String
)
