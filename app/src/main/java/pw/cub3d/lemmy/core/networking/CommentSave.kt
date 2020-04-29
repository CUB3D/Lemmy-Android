package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentSave(
    val comment_id: Int,
    val save: Boolean,
    val auth: String
)