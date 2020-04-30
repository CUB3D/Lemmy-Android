package pw.cub3d.lemmy.ui.inbox.inboxTab

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pw.cub3d.lemmy.core.data.InboxEntry
import pw.cub3d.lemmy.core.data.InboxRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject


class InboxTabViewModel @Inject constructor(
    private val inboxRepository: InboxRepository
): ViewModel() {
    val page = MutableLiveData(1L)

    val inboxResults by lazy {
        liveData<InboxEntry>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(inboxRepository.getInbox(page).asLiveData())
        }
    }
}