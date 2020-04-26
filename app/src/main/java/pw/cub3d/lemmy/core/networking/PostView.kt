package pw.cub3d.lemmy.core.networking

import android.net.Uri
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.GlobalScope
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import pw.cub3d.lemmy.core.utility.toRelativeString

@JsonClass(generateAdapter = true)
data class PostView(
    val id: Int,
    val name: String,
    val url: String?,
    val body: String?,
    val creator_id: Int,
    val community_id: Int,
    val removed: Boolean,
    val locked: Boolean,
    val published: String,
    val updated: String?, //TODO: type
    val deleted: Boolean,
    val nsfw: Boolean,
    val banned: Boolean,
    val banned_from_community: Boolean,
    val stickied: Boolean,
    val creator_name: String,
    val creator_avatar: String?,
    val community_name: String,
    val community_removed: Boolean,
    val community_deleted: Boolean,
    val community_nsfw: Boolean,
    val number_of_comments: Int,
    val score: Int,
    val upvotes: Int,
    val downvotes: Int,
    val hot_rank: Int,
    val newest_activity_time: String,
    val user_id: Int?,
    val my_vote: Int?,
    val subscribed: Boolean?,
    val read: String?, // TODO: type
    val saved: Boolean? // TODO: type
) {
    fun formattedAge() =
        Duration.between(
            LocalDateTime.now(),
            // TODO: the part following the dot is variable length: need to handle that
            LocalDateTime.parse(published.split(".").first(), DateTimeFormatter.ofPattern(
                "yyyy-MM-dd'T'HH:mm:ss"//.SSSSSS"
            ))
        ).toRelativeString()

    @Transient
    var internalThumbnail: Uri? = null
}