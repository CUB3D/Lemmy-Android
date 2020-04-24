package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailsResponse(
    val user: UserView,
    val follows: Array<CommunityFollowerView>,
//    val moderated: Array<CommunityModeratorView>,
    val comments: Array<CommentView>,
    val posts: Array<PostView>
)