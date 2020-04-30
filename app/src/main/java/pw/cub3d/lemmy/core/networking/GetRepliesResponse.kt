package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRepliesResponse(
    val replies: Array<ReplyView>
)