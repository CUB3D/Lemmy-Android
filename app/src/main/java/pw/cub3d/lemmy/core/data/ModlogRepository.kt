package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import pw.cub3d.lemmy.core.networking.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModlogRepository @Inject constructor(
    private val api: LemmyApiInterface
) {
    fun getModlog(
        page: LiveData<Long>
    ): Flow<ModLogEntry> = flow {
        page.asFlow().collect { pageNum ->
            val res = api.getModLog(null, null, pageNum, null)
            println("Got modlog(page = ${page.value}) = $res")
            if(res.isSuccessful) {
                val b = res.body()!!
                val all_posts =
                        b.removed_posts.map { ModLogEntry(ModLogEntryType.REMOVED_POST, removePostView = it) } +
                        b.locked_posts.map { ModLogEntry(ModLogEntryType.LOCKED_POST, lockedPostView = it) } +
                        b.stickied_posts.map { ModLogEntry(ModLogEntryType.STICKIED_POST, stickiedPostView = it) } +
                        b.removed_comments.map { ModLogEntry(ModLogEntryType.REMOVED_COMMENTS, removedComments = it) } +
                        b.removed_communities.map { ModLogEntry(ModLogEntryType.REMOVED_COMMUNITIES, removedCommunity = it) } +
                        b.banned_from_community.map { ModLogEntry(ModLogEntryType.BANNED_FROM_COMMUNITY, bannedFromCommunityView = it) } +
                        b.banned.map { ModLogEntry(ModLogEntryType.BANNED, bannedView = it) } +
                        b.added_to_community.map { ModLogEntry(ModLogEntryType.ADDED_TO_COMMUNITY, addedToCommunityView = it) } +
                        b.added.map { ModLogEntry(ModLogEntryType.ADDED, addedPostView = it) }
                all_posts.forEach {
                    emit(it)
                }
            }
        }
    }
}

data class ModLogEntry(
    val type: ModLogEntryType,
    val removePostView: ModRemovePostView? = null,
    val lockedPostView: ModLockPostView? = null,
    val stickiedPostView: ModStickiedPostView? = null,
    val removedComments: ModRemoveCommentView? = null,
    val removedCommunity: ModRemoveCommunityView? = null,
    val bannedFromCommunityView: ModBanFromCommunityView? = null,
    val bannedView: ModBanView? = null,
    val addedToCommunityView: ModAddCommunityView? = null,
    val addedPostView: ModAddView? = null
)

enum class ModLogEntryType(val id: Int) {
    REMOVED_POST(0),
    LOCKED_POST(1),
    STICKIED_POST(2),
    REMOVED_COMMENTS(3),
    REMOVED_COMMUNITIES(4),
    BANNED_FROM_COMMUNITY(5),
    BANNED(6),
    ADDED_TO_COMMUNITY(7),
    ADDED(8)
}