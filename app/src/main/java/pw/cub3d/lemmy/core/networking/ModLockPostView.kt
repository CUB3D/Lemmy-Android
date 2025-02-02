package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModLockPostView(
    val id: Int,
    val mod_user_id: Int,
    val post_id: Int,
    val locked: Boolean,
    val when_: String,
    val mod_user_name: String,
    val post_name: String,
    val community_id: Int,
    val community_name: String
)