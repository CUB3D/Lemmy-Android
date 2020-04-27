package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.community.CommunityFollowerView
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
            val res = api.getUserDetails(auth = authRepository.getAuthToken(), sort = "Hot", savedOnly = true, username = authRepository.getUserDetails()!!.username)
            println("Got user(self) = $res")
            if(res.isSuccessful) {
                currentUser.postValue(res.body()!!.follows)
            }
        }

        return currentUser
    }

    fun getUserDetails(userId: Int): LiveData<UserDetailsResponse> {
        val userDetails = MutableLiveData<UserDetailsResponse>()

        GlobalScope.launch {
            val res = api.getUserDetails(auth = authRepository.getAuthToken(), sort = "Hot", savedOnly = true, userId = userId)
            println("Got user(self) = $res")
            if(res.isSuccessful) {
                userDetails.postValue(res.body()!!)
            }
        }

        return userDetails
    }
}