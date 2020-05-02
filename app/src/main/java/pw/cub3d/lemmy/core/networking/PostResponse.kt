package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.community.Community

@JsonClass(generateAdapter = true)
data class PostResponse(
    val post: PostView,
    val comments: Array<CommentView>,
    val community: Community
//    val moderators: Array<CommunityModeratorView>,
//    val admins: Array<UserView>
)

