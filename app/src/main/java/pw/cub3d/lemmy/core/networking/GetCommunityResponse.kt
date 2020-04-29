package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCommunityResponse(
    val community: CommunityView,
    val moderators: Array<CommunityModeratorView>,
    val admins: Array<UserView>
)