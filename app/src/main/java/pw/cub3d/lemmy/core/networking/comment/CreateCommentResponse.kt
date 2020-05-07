package pw.cub3d.lemmy.core.networking.comment

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.comment.CommentView

@JsonClass(generateAdapter = true)
data class CreateCommentResponse(
    val comment: CommentView
)