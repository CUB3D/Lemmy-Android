package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModBanView(
    val id: Int,
    val mod_user_id: Int,
    val other_user_id: Int,
    val reason: String?,
    val banned: Boolean,
    val expires: String?,
    val when_: String,
    val mod_user_name: String,
    val other_user_name: String
)