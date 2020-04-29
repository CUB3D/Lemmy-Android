package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModAddCommunityView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val community_id: Int,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val other_user_name: String,
    val community_name: String
)