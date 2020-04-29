package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.GetCommunityResponse
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor(
    private val api: LemmyApiInterface,
    private val authRepository: AuthRepository
) {
    fun getCommunityInfo(communityId: Int): LiveData<GetCommunityResponse> {
        val ld = MutableLiveData<GetCommunityResponse>()
        GlobalScope.launch {
            val res = api.getCommunity(communityId, null, authRepository.getAuthToken()!!)
            println("GetCommunity($communityId) = $res")
            if(res.isSuccessful) {
                ld.postValue(res.body()!!)
            }
        }
        return ld
    }
}