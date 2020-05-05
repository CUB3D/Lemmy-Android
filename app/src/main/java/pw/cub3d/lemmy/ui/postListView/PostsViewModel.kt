package pw.cub3d.lemmy.ui.postListView

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.GetPostType
import pw.cub3d.lemmy.core.data.PostVote
import pw.cub3d.lemmy.core.data.PostsRepository
import pw.cub3d.lemmy.core.networking.PostView
import pw.cub3d.lemmy.core.networking.SortType
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {

    val currentPage = MutableLiveData<Int>(1)
    val community = MutableLiveData<Int?>(null)
    val type = MutableLiveData<GetPostType>()
    val sortType = MutableLiveData<SortType>()

    val saveRequest = MutableLiveData<Pair<Int, Boolean>>()
    val voteRequest = MutableLiveData<Pair<Int, PostVote>>()
    val bottomSheedState = MutableLiveData<PostView?>(null)

    val postResults by lazy {
        liveData<PostView>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(MediatorLiveData<PostView>().apply {
                addSource(
                    postsRepository.getCurrentPage(community, type, currentPage, sortType).flowOn(Dispatchers.IO)
                        .asLiveData()
                ) {
                    value = it
                }
                addSource(flow {
                    saveRequest.asFlow().collect {
                        val r = postsRepository.savePost(it.first, it.second)
                        if (r != null)
                            emit(r)
                    }
                }.flowOn(Dispatchers.IO).asLiveData()) {
                    value = it
                }
                addSource(flow {
                    voteRequest.asFlow().collect {
                        val r = postsRepository.votePost(it.first, it.second)
                        if (r != null)
                            emit(r)
                    }
                }.flowOn(Dispatchers.IO).asLiveData()) {
                    value = it
                }
            })
        }
    }

    fun onDownvote(post: PostView) {
        voteRequest.postValue(post.id to PostVote.DOWNVOTE)
    }

    fun onUpvote(post: PostView) {
        voteRequest.postValue(post.id to PostVote.UPVOTE)
    }

    fun onUnvote(post: PostView) {
        voteRequest.postValue(post.id to PostVote.NEUTRAL)
    }

    fun unSave(post: PostView) {
        saveRequest.postValue(post.id to false)
    }

    fun save(post: PostView) {
        saveRequest.postValue(post.id to true)
    }

    fun onPostLongPress(post: PostView) {
        if(bottomSheedState.value == null) {
            bottomSheedState.postValue(post)
        } else {
            if(bottomSheedState.value!!.id == post.id) {
                bottomSheedState.postValue(null)
            } else {
                bottomSheedState.postValue(post)
            }
        }
    }
}
