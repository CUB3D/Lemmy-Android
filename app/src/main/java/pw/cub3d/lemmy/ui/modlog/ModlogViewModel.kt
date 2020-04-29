package pw.cub3d.lemmy.ui.modlog

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import pw.cub3d.lemmy.core.data.ModLogEntry
import pw.cub3d.lemmy.core.data.ModlogRepository
import javax.inject.Inject

class ModlogViewModel @Inject constructor(
    private val modlogRepository: ModlogRepository
): ViewModel() {
    val page = MutableLiveData(1L)

    val modlogEntries by lazy {
        liveData<ModLogEntry>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(modlogRepository.getModlog(page).flowOn(Dispatchers.IO).asLiveData())
        }
    }
}
