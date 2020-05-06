package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostCreateRequest(
    val name: String,
    val url: String?,
    val body: String?,
    val community_id: Int,
    val auth: String
)