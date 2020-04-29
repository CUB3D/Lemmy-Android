package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentLike(
    val comment_id: Int,
    val post_id: Int,
    val score: Short,
    val auth: String
)