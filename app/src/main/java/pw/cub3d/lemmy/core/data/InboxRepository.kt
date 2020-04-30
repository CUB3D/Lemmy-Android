package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.*
import pw.cub3d.lemmy.core.networking.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InboxRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    fun markAsRead(readRequest: LiveData<Pair<Int, Boolean>>): Flow<InboxEntry> = flow {
        readRequest.asFlow().collect { request ->
            val r = api.editUserMention(EditUserMentionRequest(request.first, request.second, authRepository.getAuthToken()!!))
            println("MarkAsRead(${request.first}, ${request.second}) = $r")
            if(r.isSuccessful) {
                emit(InboxEntry(InboxEntryType.MENTION, mention = r.body()!!.mention))
            }
        }
    }

    fun markAllAsRead(): Flow<InboxEntry> = flow {
        val r = api.markAllAsRead(MarkAllAsReadRequest(authRepository.getAuthToken()!!))
        println("MarkAllAsRead() = $r")
        if (r.isSuccessful) {
            r.body()!!.replies.map { InboxEntry(InboxEntryType.REPLY, reply = it) }.forEach {
                emit(it)
            }
        }
    }

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