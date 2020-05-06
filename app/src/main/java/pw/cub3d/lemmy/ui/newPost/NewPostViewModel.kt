package pw.cub3d.lemmy.ui.newPost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.CommunityRepository
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class NewPostViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val communityRepository: CommunityRepository
): ViewModel() {

    val communities by lazy {
        communityRepository.getAllCommunities()
    }

    val submitedPostLiveData = MutableLiveData<PostView>()

    fun sendPost(
        name: String,
        url: String?,
        body: String?,
        community: Int
    ) {
        GlobalScope.launch {
//            postsRepository.createPost(name, url, body, community)?.let {
//                submitedPostLiveData.postValue(it.post)
//            }
            submitedPostLiveData.postValue(PostView(1, "", "", "", 0, 0, false, false, "", "", false, false, false, "", "", "", "", false, false, "", "", "", false, false, false, 0, 0, 0, 0, 0, "", 0, 0, false, "", false))
        }
    }
}
