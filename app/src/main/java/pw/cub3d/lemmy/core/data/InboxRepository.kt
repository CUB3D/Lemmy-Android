package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.*
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.ReplyView
import pw.cub3d.lemmy.core.networking.SortType
import pw.cub3d.lemmy.core.networking.UserMentionView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InboxRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    fun getMentions(page: LiveData<Long>): Flow<InboxEntry> = flow {
        page.asFlow().collect { pageNum ->
            val r = api.getMentions(SortType.NEW.id, pageNum, null, true, authRepository.getAuthToken()!!)
            println("GetMention($pageNum) = $r")
            if(r.isSuccessful) {
                r.body()!!.mentions.map { InboxEntry(InboxEntryType.MENTION, mention = it) }.forEach {
                    emit(it)
                }
            }
        }
    }

    fun getReplies(page: LiveData<Long>): Flow<InboxEntry> = flow {
        page.asFlow().collect { pageNum ->
            val r = api.getReplies(SortType.NEW.id, pageNum, null, true, authRepository.getAuthToken()!!)
            println("GetMention($pageNum) = $r")
            if(r.isSuccessful) {
                r.body()!!.replies.map { InboxEntry(InboxEntryType.REPLY, reply = it) }.forEach {
                    emit(it)
                }
            }
        }
    }

    fun getInbox(page: LiveData<Long>): Flow<InboxEntry> = merge(getMentions(page), getReplies(page))
}

class InboxEntry(
    val type: InboxEntryType,
    val mention: UserMentionView? = null,
    val reply: ReplyView? = null
)

enum class InboxEntryType(val id: Int) {
    REPLY(0),
    MENTION(1),
    MESSAGE(2)
}