package pw.cub3d.lemmy.core.networking.community

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.UserView

@JsonClass(generateAdapter = true)
data class GetCommunityResponse(
    val community: CommunityView,
    val moderators: Array<CommunityModeratorView>,
    val admins: Array<UserView>
)