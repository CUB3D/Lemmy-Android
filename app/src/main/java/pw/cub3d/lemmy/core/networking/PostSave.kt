package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostSave(
    val post_id: Int,
    val save: Boolean,
    val auth: String
)