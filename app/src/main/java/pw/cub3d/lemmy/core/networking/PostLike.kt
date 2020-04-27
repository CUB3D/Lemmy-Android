package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostLike(
    val post_id: Int,
    val score: Int,
    val auth: String
)