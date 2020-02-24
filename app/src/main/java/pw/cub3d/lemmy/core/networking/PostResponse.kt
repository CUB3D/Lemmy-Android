package pw.cub3d.lemmy.core.networking

import com.squareup.moshi.JsonClass
import pw.cub3d.lemmy.core.networking.PostView

@JsonClass(generateAdapter = true)
data class PostResponse(
    val posts: List<PostView>
)

