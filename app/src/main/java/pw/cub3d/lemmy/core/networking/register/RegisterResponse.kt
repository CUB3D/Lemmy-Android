package pw.cub3d.lemmy.core.networking.register

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val jwt: String
)