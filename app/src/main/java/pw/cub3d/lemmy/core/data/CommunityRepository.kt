package pw.cub3d.lemmy.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.networking.community.CommunityFollowRequest
import pw.cub3d.lemmy.core.networking.community.CommunityView
import pw.cub3d.lemmy.core.networking.GetCommunityResponse
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.community.Community
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

    fun followCommunity(followRequest: LiveData<Pair<Int, Boolean>>): Flow<CommunityView> = flow {
        followRequest.asFlow().collect { followRequest ->
            val r = api.followCommunity(
                CommunityFollowRequest(
                    followRequest.first,
                    followRequest.second,
                    authRepository.getAuthToken()!!
                )
            )
            println("FollowRequest(${followRequest.first}, ${followRequest.second}) = $r")
            if(r.isSuccessful) {
                emit(r.body()!!.community)
            }
        }
    }

    fun getAllCommunities(): LiveData<List<CommunityView>> {
        val ld = MutableLiveData<List<CommunityView>>()
        GlobalScope.launch {
            val r = api.listCommunities()
            if(r.isSuccessful) {
                ld.postValue(r.body()!!.communities)
            }
        }
        return ld
    }
}