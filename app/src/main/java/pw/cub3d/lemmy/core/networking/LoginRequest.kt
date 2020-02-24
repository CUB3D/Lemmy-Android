package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username_or_email: String,
    val password: String
)