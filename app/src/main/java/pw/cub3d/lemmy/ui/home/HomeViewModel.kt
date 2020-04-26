package pw.cub3d.lemmy.ui.home

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.core.data.UserDetailsRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
): ViewModel() {
    fun getFollowedCommunitites() = userDetailsRepository.getCurrentUserCommunities()
}