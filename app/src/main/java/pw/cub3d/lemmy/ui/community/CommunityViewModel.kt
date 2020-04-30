package pw.cub3d.lemmy.ui.community

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pw.cub3d.lemmy.core.data.CommunityRepository
import pw.cub3d.lemmy.core.networking.CommunityView
import javax.inject.Inject

class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
): ViewModel() {
    fun getSite(communityId: Int) = communityRepository.getCommunityInfo(communityId)

    val communityFollowRequest = MutableLiveData<Pair<Int, Boolean>>()

    val followResults by lazy {
        liveData<CommunityView>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(communityRepository.followCommunity(communityFollowRequest).asLiveData())
        }
    }
}
