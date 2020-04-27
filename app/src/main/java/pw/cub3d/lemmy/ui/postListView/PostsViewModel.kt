package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {

    val currentPage = MutableLiveData<Int>(1)
    val community = MutableLiveData<Int?>(null)

    val postResults by lazy {
        liveData<PostView>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(postsRepository.getCurrentPage(community, currentPage).asLiveData())
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
        postsRepository.savePost(post.id, true)
    }
}
