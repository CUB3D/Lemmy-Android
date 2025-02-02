package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditUserMentionRequest(
    val user_mention_id: Int,
    val read: Boolean?,
    val auth: String
)