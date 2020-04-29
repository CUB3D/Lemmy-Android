package pw.cub3d.lemmy.ui.community

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.CommunityRepository
import javax.inject.Inject

class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
): ViewModel() {
    fun getSite(communityId: Int) = communityRepository.getCommunityInfo(communityId)
}
