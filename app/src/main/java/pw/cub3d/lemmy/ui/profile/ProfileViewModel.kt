package pw.cub3d.lemmy.ui.profile

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.core.data.UserDetailsRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDetailsRepository: UserDetailsRepository
): ViewModel() {
    fun getUserClaims() = authRepository.getUserDetails()!!

    fun getUserDetails(userId: Int) = userDetailsRepository.getUserDetails(userId)
}
