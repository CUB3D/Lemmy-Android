package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SiteView(
    val id: Int,
    val name: String,
    val description: String,
    val creator_id: Int,
    val published: String,
    val updated: String,
    val enable_downvotes: Boolean,
    val open_registration: Boolean,
    val enable_nsfw: Boolean,
    val creator_name: String,
    val creator_avatar: String,
    val number_of_users: Int,
    val number_of_posts: Int,
    val number_of_comments: Int,
    val number_of_communities: Int
)