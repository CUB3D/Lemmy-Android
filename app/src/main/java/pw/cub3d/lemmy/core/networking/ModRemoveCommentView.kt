package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModRemoveCommentView(
    val id: Int,
    val mod_user_id: Int,
    val comment_id: Int,
    val reason: String?,
    val removed: Boolean,
    val when_: String,
    val mod_user_name: String,
    val comment_user_id: Int,
    val comment_user_name: String,
    val comment_content: String,
    val post_id: Int,
    val post_name: String,
    val community_id: Int?,
    val community_name: String
)