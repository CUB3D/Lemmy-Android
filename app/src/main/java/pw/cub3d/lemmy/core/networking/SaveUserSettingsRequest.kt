package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveUserSettingsRequest(
    val show_nsfw: Boolean,
    val theme: String,
    val default_sort_type: Short,
    val default_listings_type: Short,
    val lang: String,
    val avatar: String?,
    val email: String?,
    val matrix_user_id: String?,
    val new_password: String?,
    val new_password_verify: String?,
    val old_password: String?,
    val show_avatars: Boolean,
    val send_notifications_to_email: Boolean,
    val auth: String
)