package pw.cub3d.lemmy.core.networking.user

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.core.networking.UserView
import pw.cub3d.lemmy.core.networking.community.CommunityFollowerView

@JsonClass(generateAdapter = true)
data class UserDetailsResponse(
    val user: UserView,
    val follows: Array<CommunityFollowerView>,
//    val moderated: Array<CommunityModeratorView>,
    val comments: Array<CommentView>,
    val posts: Array<PostView>
)