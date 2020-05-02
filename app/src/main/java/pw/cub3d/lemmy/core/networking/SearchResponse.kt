package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val type_: String,
    val comments: Array<CommentView>,
    val posts: Array<PostView>,
    val communities: Array<CommunityView>,
    val users: Array<UserView>
)