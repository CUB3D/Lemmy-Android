package pw.cub3d.lemmy.core.networking.comment

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateCommentRequest(
    val content: String,
    val parent: Int?,
    val edit_id: Int?,
    val post_id: Int,
    val auth: String
)