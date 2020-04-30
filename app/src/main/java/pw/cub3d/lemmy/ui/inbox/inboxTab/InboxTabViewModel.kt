package pw.cub3d.lemmy.ui.inbox.inboxTab

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import pw.cub3d.lemmy.core.data.InboxEntry
import pw.cub3d.lemmy.core.data.InboxRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject


class InboxTabViewModel @Inject constructor(
    private val inboxRepository: InboxRepository
): ViewModel() {
    fun markAllAsRead() {
        markAllReadRequest.postValue(true)
    }

    fun markAsRead(id: Int, read: Boolean) {
        readRequest.postValue(id to read)
    }

    val page = MutableLiveData(1L)
    private val markAllReadRequest = MutableLiveData<Boolean>()
    private val readRequest = MutableLiveData<Pair<Int, Boolean>>()

    val inboxResults by lazy {
        liveData<InboxEntry>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(MediatorLiveData<InboxEntry>().apply {
                addSource(inboxRepository.getInbox(page).asLiveData()) { value = it }
                addSource(markAllReadRequest.asFlow().flatMapConcat { inboxRepository.markAllAsRead() }.asLiveData()) { value = it }
                addSource(inboxRepository.markAsRead(readRequest).asLiveData()) { value = it }
            })
        }
    }
}