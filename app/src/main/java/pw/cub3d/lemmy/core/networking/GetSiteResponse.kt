package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSiteResponse(
    val site: SiteView?,
    val admins: Array<UserView>,
    val banned: Array<UserView>,
    val online: Int
)