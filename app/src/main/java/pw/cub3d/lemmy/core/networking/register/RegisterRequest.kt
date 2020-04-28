package pw.cub3d.lemmy.core.networking.register

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    val username: String,
    val email: String?,
    val password: String,
    val password_verify: String,
    val admin: Boolean = false,
    val show_nsfw: Boolean = false
)