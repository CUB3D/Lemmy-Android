package pw.cub3d.lemmy.core.networking.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val jwt: String
)