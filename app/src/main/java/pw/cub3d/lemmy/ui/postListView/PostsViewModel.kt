package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {

    val currentPage = MutableLiveData<Int>(1)
    val community = MutableLiveData<Int?>(null)
    val saveRequest = MutableLiveData<Pair<Int, Boolean>>()

    val postResults by lazy {
        liveData<PostView>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(MediatorLiveData<PostView>().apply {
                addSource(postsRepository.getCurrentPage(community, currentPage).asLiveData()) {
                    value = it
                }
                addSource(postsRepository.savePost(saveRequest).asLiveData()) {
                    value = it
                }
            })
        }
    }

    fun onDownvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.DOWNVOTE)
    }

    fun onUpvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.UPVOTE)
    }

    fun onUnvote(post: PostView) {
        postsRepository.votePost(post.id, PostVote.NEUTRAL)
    }

    fun unSave(post: PostView) {
        postsRepository.savePost(post.id, false)
    }

    fun save(post: PostView) {
        saveRequest.postValue(post.id to true)
    }
}
