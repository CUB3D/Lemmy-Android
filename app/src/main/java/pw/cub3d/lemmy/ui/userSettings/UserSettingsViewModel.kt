package pw.cub3d.lemmy.ui.userSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.core.data.UserDetailsRepository
import pw.cub3d.lemmy.core.networking.user.UserClaims
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val userClaimsLiveData = MutableLiveData(authRepository.getUserDetails()!!)

    fun getUserClaims(): LiveData<UserClaims> = authRepository.getLiveUserDetails().map { it!! }

    fun setNSFW(nsfw: Boolean) {
        val details = userClaimsLiveData.value!!
        userDetailsRepository.saveUserSettings(
            nsfw,
            "darkly",
            details.default_listing_type.toShort(),
            details.default_listing_type.toShort(),
            details.lang,
            details.show_avatars
        )
    }
}