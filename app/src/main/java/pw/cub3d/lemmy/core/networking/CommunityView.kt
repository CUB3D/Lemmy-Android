package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityView(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val category_id: Int,
    val creator_id: Int,
    val removed: Boolean,
    val published: String,
    val updated: String?,
    val deleted: Boolean,
    val nsfw: Boolean,
    val creator_name: String,
    val creator_avatar: String,
    val category_name: String,
    val number_of_subscribers: Int,
    val number_of_posts: Int,
    val number_of_comments: Int,
    val hot_rank: Int,
    val user_id: Int,
    val subscribed: Boolean
)