package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModStickiedPostView(
    val id: Int,
    val mod_user_id: Int,
    val post_id: Int,
    val stickied: Boolean,
    val when_: String,
    val mod_user_name: String,
    val community_id: Int?,
    val community_name: String
)