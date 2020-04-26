package pw.cub3d.lemmy.core.networking.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username_or_email: String,
    val password: String
)