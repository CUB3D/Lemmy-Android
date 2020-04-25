package pw.cub3d.lemmy.ui.profile

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.AuthRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun getUserClaims() = authRepository.getUserDetails()!!
}
