package pw.cub3d.lemmy.core.networking.community

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Community(
    val id: Int,
    val name: String,
    val title: String,
    val description: String?,
    val category_id: Int,
    val creator_id: Int,
    val creator_name: String,
    val creator_avater: String?,
    val category_name: String,
    val number_of_subscribers: Int,
    val number_of_posts: Int,
    val number_of_comments: Int,
    val hot_rank: Int,
    val user_id: Int?,
    val subscribed: Boolean?
)