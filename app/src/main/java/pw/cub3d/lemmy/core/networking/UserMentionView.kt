package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserMentionView(
    val id: Int,
    val user_mention_id: Int,
    val creator_id: Int,
    val post_id: Int,
    val parent_id: Int?,
    val content: String,
    val removed: Boolean,
    val read: Boolean,
    val published: String,
    val updated: String?,
    val deleted: Boolean,
    val community_id: Int,
    val community_name: String,
    val banned: Boolean,
    val banned_from_community: Boolean,
    val creator_name: String,
    val score: Int,
    val upvotes: Int,
    val downvotes: Int,
    val hot_rank: Int,
    val user_id: Int,
    val my_vote: Int,
    val saved: Boolean?,
    val recipient_id: Int
)