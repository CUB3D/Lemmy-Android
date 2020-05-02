package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.comment.CommentView
import pw.cub3d.lemmy.core.networking.community.CommunityView

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val type_: String,
    val comments: Array<CommentView>,
    val posts: Array<PostView>,
    val communities: Array<CommunityView>,
    val users: Array<UserView>
)