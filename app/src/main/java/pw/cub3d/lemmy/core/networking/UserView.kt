package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserView(
    val id: Int,
    val name: String,
    val avater: String?,
    val email: String,
    val matrix_user_id: String?,
    val admin: Boolean,
    val banned: Boolean,
    val show_avatars: Boolean,
    val send_notifications_to_email: Boolean,
    val published: String,
    val number_of_posts: Int,
    val post_score: Int,
    val number_of_comments: Int,
    val comment_score: Int
)
