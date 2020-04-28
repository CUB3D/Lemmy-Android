package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.SaveUserSettingsRequest
import pw.cub3d.lemmy.core.networking.community.CommunityFollowerView
import pw.cub3d.lemmy.core.networking.user.UserClaims
import pw.cub3d.lemmy.core.networking.user.UserDetailsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    fun getCurrentUserCommunities(): LiveData<Array<CommunityFollowerView>> {
        val currentUser = MutableLiveData<Array<CommunityFollowerView>>()

        GlobalScope.launch {
            val res = api.getUserDetails(
                auth = authRepository.getAuthToken(),
                sort = "Hot",
                savedOnly = true,
                username = authRepository.getUserDetails()!!.username
            )
            println("Got user(self) = $res")
            if (res.isSuccessful) {
                currentUser.postValue(res.body()!!.follows)
            }
        }

        return currentUser
    }

    fun getUserDetails(userId: Int): LiveData<UserDetailsResponse> {
        val userDetails = MutableLiveData<UserDetailsResponse>()

        GlobalScope.launch {
            val res = api.getUserDetails(
                auth = authRepository.getAuthToken(),
                sort = "Hot",
                savedOnly = false,
                userId = userId
            )
            println("Got user(self) = $res")
            if (res.isSuccessful) {
                userDetails.postValue(res.body()!!)
            }
        }

        return userDetails
    }

    fun getCurrentUserDetails(): LiveData<UserDetailsResponse> {
        val userDetails = MutableLiveData<UserDetailsResponse>()

        GlobalScope.launch {
            val res = api.getUserDetails(
                auth = authRepository.getAuthToken(),
                sort = "Hot",
                savedOnly = false,
                username = authRepository.getUserDetails()!!.username
            )
            println("Got user(self) = $res")
            if (res.isSuccessful) {
                userDetails.postValue(res.body()!!)
            }
        }

        return userDetails
    }

    fun saveUserSettings(
        showNsfw: Boolean,
        theme: String,
        defaultSortType: Short,
        defaultListingType: Short,
        lang: String,
        showAvatars: Boolean
    ) {
        GlobalScope.launch {
            val res = api.saveUserSettings(
                SaveUserSettingsRequest(
                    showNsfw,
                    theme,
                    defaultSortType,
                    defaultListingType,
                    lang,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    showAvatars,
                    send_notifications_to_email = false,
                    auth = authRepository.getAuthToken()!!
                )
            )
            println("SaveUserSettings($showNsfw, $theme, $defaultSortType, $defaultListingType) = $res")

            if (res.isSuccessful) {
                authRepository.setJWT(res.body()!!.jwt)
            }
        }
    }
}