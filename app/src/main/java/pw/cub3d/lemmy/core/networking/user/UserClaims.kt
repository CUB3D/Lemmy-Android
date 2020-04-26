package pw.cub3d.lemmy.core.networking.user

data class UserClaims(
    val id: Int,
    val username: String,
    val show_nsfw: Boolean,
    val default_sort_type: Int,
    val default_listing_type: Int,
    val lang: String,
    val avatar: String?,
    val show_avatars: Boolean
)