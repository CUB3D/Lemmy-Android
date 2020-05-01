package pw.cub3d.lemmy.ui.search

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pw.cub3d.lemmy.core.data.SearchRepository
import pw.cub3d.lemmy.core.data.SearchResultEntry
import pw.cub3d.lemmy.core.data.SearchType
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    val queryLiveData = MutableLiveData<String>()
    val searchTypeLiveData = MutableLiveData(SearchType.ALL)
    val pageLiveData = MutableLiveData(1L)

    val searchResults by lazy {
        liveData<SearchResultEntry>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                searchRepository.search(queryLiveData, searchTypeLiveData, pageLiveData)
                    .asLiveData()
            )
        }
    }

}