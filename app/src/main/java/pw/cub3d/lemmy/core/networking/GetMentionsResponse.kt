package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMentionsResponse(
    val mentions: Array<UserMentionView>
)